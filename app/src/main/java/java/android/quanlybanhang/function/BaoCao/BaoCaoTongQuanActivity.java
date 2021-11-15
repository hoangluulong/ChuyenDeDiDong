package java.android.quanlybanhang.function.BaoCao;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.util.Pair;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.android.quanlybanhang.Common.FormatDate;
import java.android.quanlybanhang.Common.SupportFragmentDonOnline;
import java.android.quanlybanhang.Common.ThongTinCuaHangSql;
import java.android.quanlybanhang.Common.Utils;
import java.android.quanlybanhang.Model.PieTongQuan;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.database.DbBaoCao;
import java.android.quanlybanhang.function.DonHangOnline.data.DonHang;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;


public class BaoCaoTongQuanActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {


    private String ID_CUAHNAG;
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
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout layoutProgressBar;

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
    private String ngayBD;
    private String ngayKT;
    private String ngayKetThuc;
    private Calendar calendar2;
    private int id;
    private ArrayList<PieTongQuan> dsSanPham = new ArrayList<>();
    private ArrayList<String> dsNhomSanPham = new ArrayList<>();
    private ArrayList<String> listDays;
    private ArrayList<DataSnapshot> dataBienLai = new ArrayList<>();
    //    private ArrayList<ChiTieu> listChi = new ArrayList<>();
    private Double tongChi = 0.0;
    private ArrayList<PieTongQuan> sanPham = new ArrayList<>();
    private MaterialDatePicker materialDatePicker;
    private int tamp = 0;

    //dialog
    private Dialog dialog;
    private Button dong;
    private Button dongy;

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
        ThongTinCuaHangSql thongTinCuaHangSql = new ThongTinCuaHangSql(this);
        ID_CUAHNAG = thongTinCuaHangSql.IDCuaHang();

        DatabaseSQlite();

        getDataFireBase();
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

//        guiDuLieu();
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

        //button
        thoiGianLamViec = findViewById(R.id.btnThoiGianLamViec);
        btnChiNhanh = findViewById(R.id.btnChiNhanh);

        //Dialog
        dialog = new Dialog(this);

        pieChart = (PieChart) findViewById(R.id.pieChart);
        swipeRefreshLayout = findViewById(R.id.refresh);
        layoutProgressBar = findViewById(R.id.layoutProgressBar);

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
        thoiGianLamViec.setOnClickListener(this);
        btnChiNhanh.setOnClickListener(this);

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
                intent = new Intent(BaoCaoTongQuanActivity.this, BaoCaoChiSoActivity.class);
                startActivity(intent);
                bottomSheetDialog.dismiss();
                break;
            case R.id.viewBanChay:
                Toast.makeText(BaoCaoTongQuanActivity.this, "Báo cáo sản phẩm bán chạy", Toast.LENGTH_LONG).show();
                bottomSheetDialog.dismiss();
                intent = new Intent(BaoCaoTongQuanActivity.this, BaoCaoSanPhamActivity.class);
                startActivity(intent);
                break;
            case R.id.viewHoaDon:
                Toast.makeText(BaoCaoTongQuanActivity.this, "Báo cáo hóa đơn", Toast.LENGTH_LONG).show();
                intent = new Intent(BaoCaoTongQuanActivity.this, DanhSachBienLaiActivity.class);
                startActivity(intent);
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
            case R.id.btnThoiGianLamViec:
                openFeedbackDialog(Gravity.CENTER);
                break;
            case R.id.btnChiNhanh:
                Toast.makeText(BaoCaoTongQuanActivity.this, "Chi nhánh", Toast.LENGTH_LONG).show();
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


    /**
     * SQLite
     * gọi hàm MangNgay, CustomNgay2
     */
    private void DatabaseSQlite() {
        // Tạo database
        dataSql = new DbBaoCao(this, "app_database.sqlite", null, 2);
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
                tamp = kieuHienThi;
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

        if (kieuHienThi == 1) {
            thoiGianLamViec.setText("Hôm nay");
        } else if (kieuHienThi == 2) {
            thoiGianLamViec.setText("Tuần này");
        } else if (kieuHienThi == 3) {
            thoiGianLamViec.setText("Tháng này");
        } else if (kieuHienThi == 4) {
            thoiGianLamViec.setText("Năm nay");
        } else if (kieuHienThi == 5) {
            thoiGianLamViec.setText(ngayBatDau + "-" + ngayKetThuc);
        }

        listDays = MangNgay();
        getDataBienLai();
        SetDuLieu();
        swipeRefreshLayout.setOnRefreshListener(BaoCaoTongQuanActivity.this);
    }

    /**
     * - get data tổng tiền
     * - get data hóa đơn
     * - get data danh sach sản phẩm
     * Data
     */

    private ArrayList<Integer> checkThu = new ArrayList<Integer>();
    private ArrayList<Integer> checkChi = new ArrayList<Integer>();

    private void getDataBienLai() {
        listDays.clear();
        listDays = MangNgay();
        dataBienLai.clear();
        tongChi = 0.0;
        checkThu.clear();
        checkChi.clear();

        for (String st : listDays) {
            mFirebaseDatabase.child("CuaHangOder/" + ID_CUAHNAG + "/bienlai/thu/" + st).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        for (DataSnapshot a : dataSnapshot.getChildren()) {
                            dataBienLai.add(a);
                        }
                        checkThu.add(1);
                    } else {
                        checkThu.add(0);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }

            });

            mFirebaseDatabase.child("CuaHangOder/" + ID_CUAHNAG + "/bienlai/chi/" + st).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            tongChi += Double.parseDouble(postSnapshot.child("tongchi").getValue().toString());
                        }
                        checkChi.add(1);
                    } else {
                        checkChi.add(0);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        mFirebaseDatabase.child("CuaHangOder/" + ID_CUAHNAG + "/bienlai/taichinh").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    tien_tongtien.setText(formatStr(Double.parseDouble(dataSnapshot.child("tongTien").getValue().toString())));
                    tien_nophaithu.setText(formatStr(Double.parseDouble(dataSnapshot.child("noPhaiTra").getValue().toString())));
                    tien_nophaitra.setText(formatStr(Double.parseDouble(dataSnapshot.child("noPhaiThu").getValue().toString())));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        getDataFireBase();
    }

    int i = 0;

    private void SetDuLieu() {
        dsSanPham.clear();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int demChi = 0;
                int demThu = 0;
                if (checkChi.size() == listDays.size() && checkThu.size() == listDays.size()) {
                    layoutProgressBar.setVisibility(View.GONE);
                    for (int i = 0; i < listDays.size(); i++) {
                        if (checkThu.get(i) == 0) {
                            demThu++;
                        }
                        if (checkChi.get(i) == 0) {
                            demChi++;
                        }
                    }
                    if (demChi == listDays.size() && demThu == listDays.size()) {

                        i = 0;
                        tien_doanhthu.setText("0");
                        tien_doanhthu.setText("0");

                        tien_dathanhtoan.setText("0");
                        sl_dathanhtoan.setText("0");

                        tien_doanhso.setText("0");
                        sl_doanhso.setText("0");

                        tien_chuathanhtoan.setText("0");
                        sl_chuathanhtoan.setText("0");

                        tien_chitieu.setText("0");

                        swipeRefreshLayout.setRefreshing(false);
                        bieuDoSanPham();
                        dialog.dismiss();
                    } else {

                        int slDaThanhToan = 0;
                        double tienDTT = 0;
                        int slChuaThanhToan = 0;
                        int tienCTT = 0;
                        int slDichVuPhuThu = 0;
                        double tienDVPT = 0;
                        int slBiHuy = 0;
                        double tienHoaDonBiHuy = 0;


                        ArrayList<DataSnapshot> listSP = new ArrayList<>();

                        for (DataSnapshot a : dataBienLai) {
                            listSP.add(a.child("sanpham"));
                            if (Integer.parseInt(a.child("status").getValue() + "") == 1) {
                                slDaThanhToan++;
                                tienDTT += Double.parseDouble(a.child("tongtien").getValue() + "");
                            } else if (Integer.parseInt(a.child("status").getValue() + "") == 2) {
                                slChuaThanhToan++;
                                tienCTT += Double.parseDouble(a.child("tongtien").getValue() + "");
                            } else if (Integer.parseInt(a.child("status").getValue() + "") == 3) {
                            }
                            if (Integer.parseInt(a.child("status").getValue() + "") == 4) {
                                slBiHuy++;
                                tienHoaDonBiHuy += Double.parseDouble(a.child("tongtien").getValue() + "");
                            }
                            if (Integer.parseInt(a.child("status").getValue() + "") == 5) {

                            }
                        }

                        tien_doanhthu.setText(formatStr(tienDTT - tongChi));

                        tien_dathanhtoan.setText(formatStr(tienDTT - tienCTT));
                        sl_dathanhtoan.setText((slDaThanhToan - slChuaThanhToan) + "");

                        tien_doanhso.setText(formatStr(tienDTT));
                        sl_doanhso.setText(slDaThanhToan + "");

                        tien_chuathanhtoan.setText(formatStr(tienCTT));
                        sl_chuathanhtoan.setText(slChuaThanhToan + "");

                        sl_dichvuphuthu.setText(slDichVuPhuThu + "");
                        tien_dichvuphuthu.setText(formatStr(tienDVPT));

                        sl_comonhuy.setText(slBiHuy + "");
                        tien_comonhuy.setText(tienHoaDonBiHuy + "");

                        tien_chitieu.setText(formatStr(tongChi));

                        if (kieuHienThi == 1) {
                            thoiGianLamViec.setText("Hôm nay");
                        } else if (kieuHienThi == 2) {
                            thoiGianLamViec.setText("Tuần này");
                        } else if (kieuHienThi == 3) {
                            thoiGianLamViec.setText("Tháng này");
                        } else if (kieuHienThi == 4) {
                            thoiGianLamViec.setText("Năm nay");
                        } else if (kieuHienThi == 5) {
                            thoiGianLamViec.setText(ngayBatDau + "-" + ngayKetThuc);
                        }

                        ArrayList<String> list = new ArrayList<>();
                        for (DataSnapshot sp : listSP) {
                            for (DataSnapshot s : sp.getChildren()) {
                                sanPham.add(new PieTongQuan(s.child("nameProduct").getValue().toString(), Integer.parseInt(s.child("soluong").getValue().toString())));
                                list.add(s.child("nameProduct").getValue().toString());
                            }
                        }

                        int count = list.size();
                        for (int i = 0; i < count; i++) {
                            for (int j = i + 1; j < count; j++) {
                                if (list.get(i).equals(list.get(j))) {
                                    list.remove(j--);
                                    count--;
                                }
                            }
                        }

                        for (int i = 0; i < list.size(); i++) {
                            dsSanPham.add(new PieTongQuan(list.get(i), 0));
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
                        i = 0;
                        swipeRefreshLayout.setRefreshing(false);
                        dialog.dismiss();
                    }
                } else {
                    i++;
                    if (i <= 500) {
                        SetDuLieu();
                    } else {
                        i = 0;
                        tien_doanhthu.setText("0");
                        tien_doanhthu.setText("0");

                        tien_dathanhtoan.setText("0");
                        sl_dathanhtoan.setText("0");

                        tien_doanhso.setText("0");
                        sl_doanhso.setText("0");

                        tien_chuathanhtoan.setText("0");
                        sl_chuathanhtoan.setText("0");

                        tien_chitieu.setText("0");

                        swipeRefreshLayout.setRefreshing(false);
                        bieuDoSanPham();
                        layoutProgressBar.setVisibility(View.GONE);
                        dialog.dismiss();
                    }

                }
            }
        }, 50);
    }

    private String formatStr(double val) {
        return String.format(Locale.US, "%,.2f", val);
    }

    private void openFeedbackDialog(int gravity) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_ngay_bao_bao);
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        ngayBD = ngayBatDau;
        ngayKT = ngayKetThuc;

        Window window = dialog.getWindow();

        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        RadioGroup gRadio = dialog.findViewById(R.id.gRadio);
        dong = dialog.findViewById(R.id.btn_dong);
        dongy = dialog.findViewById(R.id.btn_dong_y);
        Button TuyChonNgay = dialog.findViewById(R.id.btn_chon_ngay);
        RadioButton rdHomNay = dialog.findViewById(R.id.rdb_homnay);
        RadioButton rdTuanNay = dialog.findViewById(R.id.rdb_tuannay);
        RadioButton rdThangNay = dialog.findViewById(R.id.rdb_thangnay);
        RadioButton rdNamNay = dialog.findViewById(R.id.rdb_namnay);

        if (kieuHienThi == 1) {
            rdHomNay.setChecked(true);
            rdTuanNay.setChecked(false);
            rdThangNay.setChecked(false);
            rdNamNay.setChecked(false);
        } else if (kieuHienThi == 2) {
            rdHomNay.setChecked(false);
            rdTuanNay.setChecked(true);
            rdThangNay.setChecked(false);
            rdNamNay.setChecked(false);
        } else if (kieuHienThi == 3) {
            rdHomNay.setChecked(false);
            rdTuanNay.setChecked(false);
            rdThangNay.setChecked(true);
            rdNamNay.setChecked(false);
        } else if (kieuHienThi == 4) {
            rdHomNay.setChecked(false);
            rdTuanNay.setChecked(false);
            rdThangNay.setChecked(false);
            rdNamNay.setChecked(true);
        } else if (kieuHienThi == 5) {
            rdHomNay.setChecked(false);
            rdTuanNay.setChecked(false);
            rdThangNay.setChecked(false);
            rdNamNay.setChecked(false);
            TuyChonNgay.setText(ngayBatDau + " - " + ngayKetThuc);
        }

        Long toDay = MaterialDatePicker.todayInUtcMilliseconds();

        calendar2 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar2.clear();

        calendar2.setTimeInMillis(toDay);

        calendar2.set(Calendar.YEAR, 2000);
        Long year = calendar2.getTimeInMillis();

        CalendarConstraints.Builder contraBuilder = new CalendarConstraints.Builder();
        contraBuilder.setStart(year);
        contraBuilder.setEnd(toDay);
        contraBuilder.setValidator(DateValidatorPointBackward.before(toDay));

        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("SELECT A DATE");
        builder.setCalendarConstraints(contraBuilder.build());
        materialDatePicker = builder.build();

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long, Long> selection) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String startDate = formatter.format(selection.first);
                String endDate = formatter.format(selection.second);
                TuyChonNgay.setText(startDate + " - " + endDate);
                ngayBD = startDate;
                ngayKT = endDate;
                rdHomNay.setChecked(false);
                rdTuanNay.setChecked(false);
                rdThangNay.setChecked(false);
                rdNamNay.setChecked(false);
                tamp = 5;
            }
        });

        dong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                tamp = kieuHienThi;
            }
        });

        dongy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ngayBatDau = ngayBD;
                ngayKetThuc = ngayKT;
                dataSql = new DbBaoCao(BaoCaoTongQuanActivity.this, "app_database.sqlite", null, 2);
                dataSql.QueryData("UPDATE KieuHienThiBaoCao SET NgayBatDau='" + ngayBatDau + "', NgayKetThuc='" + ngayKetThuc + "', KieuHienThi=" + tamp + " WHERE id=" + id + "");
                kieuHienThi = tamp;

                getDataBienLai();
                SetDuLieu();
                if (kieuHienThi == 1) {
                    thoiGianLamViec.setText("Hôm nay");
                } else if (kieuHienThi == 2) {
                    thoiGianLamViec.setText("Tuần này");
                } else if (kieuHienThi == 3) {
                    thoiGianLamViec.setText("Tháng này");
                } else if (kieuHienThi == 4) {
                    thoiGianLamViec.setText("Năm nay");
                } else if (kieuHienThi == 5) {
                    thoiGianLamViec.setText(ngayBatDau + "-" + ngayKetThuc);
                }

                dongy.setEnabled(false);
                Toast.makeText(BaoCaoTongQuanActivity.this, ngayBatDau + " -- " + ngayKetThuc, Toast.LENGTH_SHORT).show();
            }
        });

        rdHomNay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdHomNay.setChecked(true);
                rdTuanNay.setChecked(false);
                rdThangNay.setChecked(false);
                rdNamNay.setChecked(false);
                TuyChonNgay.setText("Chọn một ngày khác");
                ngayBD = formatDate(date);
                ngayKT = formatDate(date);
                tamp = 1;
            }
        });

        rdTuanNay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdHomNay.setChecked(false);
                rdTuanNay.setChecked(true);
                rdThangNay.setChecked(false);
                rdNamNay.setChecked(false);
                TuyChonNgay.setText("Chọn một ngày khác");

                // Get calendar set to current date and time
                Calendar c = GregorianCalendar.getInstance();

                // Set the calendar to monday of the current week
                c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

                // Print dates of the current week starting on Monday
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

                ngayBD = df.format(c.getTime());
                c.add(Calendar.DATE, 6);
                ngayKT = df.format(c.getTime());
                tamp = 2;
            }
        });

        rdThangNay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdHomNay.setChecked(false);
                rdTuanNay.setChecked(false);
                rdThangNay.setChecked(true);
                rdNamNay.setChecked(false);
                TuyChonNgay.setText("Chọn một ngày khác");

                int month = Calendar.getInstance().get(Calendar.MONTH);
                int year = Calendar.getInstance().get(Calendar.YEAR);
                String thang = (month + 1) + "";
                if ((month + 1) < 10) {
                    thang = "0" + (month + 1);
                }

                ngayBD = "01/" + thang + "/" + year;
                ngayKT = formatDate(date);
                tamp = 3;
            }
        });

        rdNamNay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdHomNay.setChecked(false);
                rdTuanNay.setChecked(false);
                rdThangNay.setChecked(false);
                rdNamNay.setChecked(true);
                TuyChonNgay.setText("Chọn một ngày khác");

                int year = Calendar.getInstance().get(Calendar.YEAR);
                ngayBD = "01/01/" + year;
                ngayKT = formatDate(date);
                tamp = 4;
            }
        });

        TuyChonNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TuyChonNgay.setEnabled(false);
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
                Utils.delay(2, new Utils.DelayCallback() {
                    @Override
                    public void afterDelay() {
                        TuyChonNgay.setEnabled(true);
                    }
                });
            }
        });

        dialog.show();
    }

    @Override
    public void onRefresh() {
        getDataBienLai();
        SetDuLieu();
    }

    private String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(date);
        return strDate;
    }

    private void guiDuLieu() {
        int dem = 1;
        String tenSP = "Bánh kếp";
        String nhomSP = "Bánh";

        int min = 5;
        int max = 10;

        Random r = new Random();
        int sl = r.nextInt(max - min + 1) + min;
        Double gia = sl * 20000.0;
        for (String a : listDays) {
            if (dem == 2) {
                tenSP = "Trà chanh";
                nhomSP = "Nước uống";
                sl = r.nextInt(max - min + 1) + min;
                gia = sl * 20000.0;
            } else if (dem == 3) {
                tenSP = "Bia Tiger";
                nhomSP = "Nước uống";

                sl = r.nextInt(max - min + 1) + min;
                gia = sl * 20000.0;
            } else if (dem == 4) {
                tenSP = "Bia Heineken";
                nhomSP = "abcxyz";
                sl = r.nextInt(max - min + 1) + min;
                gia = sl * 20000.0;
            } else {
                tenSP = "RedBull";
                sl = r.nextInt(max - min + 1) + min;
                gia = sl * 20000.0;
            }

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            timestamp.getTime();

            mFirebaseDatabase.child("CuaHangOder/Meskv6p2bkf89ferNygy5Kp1aAA3/bienlai/thu/" + a + "/" + timestamp.getTime() + "/id_ban").setValue("b2");
            mFirebaseDatabase.child("CuaHangOder/Meskv6p2bkf89ferNygy5Kp1aAA3/bienlai/thu/" + a + "/" + timestamp.getTime() + "/id_khuvuc").setValue("k2");
            mFirebaseDatabase.child("CuaHangOder/Meskv6p2bkf89ferNygy5Kp1aAA3/bienlai/thu/" + a + "/" + timestamp.getTime() + "/id_khachhang").setValue("kh2");
            mFirebaseDatabase.child("CuaHangOder/Meskv6p2bkf89ferNygy5Kp1aAA3/bienlai/thu/" + a + "/" + timestamp.getTime() + "/id_nhanvien").setValue("nv2");
            mFirebaseDatabase.child("CuaHangOder/Meskv6p2bkf89ferNygy5Kp1aAA3/bienlai/thu/" + a + "/" + timestamp.getTime() + "/khuyenmai/id_khuyenmai").setValue(0);
            mFirebaseDatabase.child("CuaHangOder/Meskv6p2bkf89ferNygy5Kp1aAA3/bienlai/thu/" + a + "/" + timestamp.getTime() + "/khuyenmai/status").setValue(0);
            mFirebaseDatabase.child("CuaHangOder/Meskv6p2bkf89ferNygy5Kp1aAA3/bienlai/thu/" + a + "/" + timestamp.getTime() + "/khuyenmai/value").setValue(0);
            mFirebaseDatabase.child("CuaHangOder/Meskv6p2bkf89ferNygy5Kp1aAA3/bienlai/thu/" + a + "/" + timestamp.getTime() + "/phiphu/id_" + dem + "/mieuta").setValue("null");
            mFirebaseDatabase.child("CuaHangOder/Meskv6p2bkf89ferNygy5Kp1aAA3/bienlai/thu/" + a + "/" + timestamp.getTime() + "/phiphu/id_" + dem + "/tienphi").setValue("null");
            mFirebaseDatabase.child("CuaHangOder/Meskv6p2bkf89ferNygy5Kp1aAA3/bienlai/thu/" + a + "/" + timestamp.getTime() + "/sanpham/id_" + dem + "/tensanpham").setValue(tenSP);
            mFirebaseDatabase.child("CuaHangOder/Meskv6p2bkf89ferNygy5Kp1aAA3/bienlai/thu/" + a + "/" + timestamp.getTime() + "/sanpham/id_" + dem + "/nhomsanpham").setValue(nhomSP);
            mFirebaseDatabase.child("CuaHangOder/Meskv6p2bkf89ferNygy5Kp1aAA3/bienlai/thu/" + a + "/" + timestamp.getTime() + "/sanpham/id_" + dem + "/giamgia").setValue(0);
            mFirebaseDatabase.child("CuaHangOder/Meskv6p2bkf89ferNygy5Kp1aAA3/bienlai/thu/" + a + "/" + timestamp.getTime() + "/sanpham/id_" + dem + "/giatien").setValue(20000);
            mFirebaseDatabase.child("CuaHangOder/Meskv6p2bkf89ferNygy5Kp1aAA3/bienlai/thu/" + a + "/" + timestamp.getTime() + "/sanpham/id_" + dem + "/soluong").setValue(sl);
            mFirebaseDatabase.child("CuaHangOder/Meskv6p2bkf89ferNygy5Kp1aAA3/bienlai/thu/" + a + "/" + timestamp.getTime() + "/sanpham/id_" + dem + "/thanhtien").setValue(gia);
            mFirebaseDatabase.child("CuaHangOder/Meskv6p2bkf89ferNygy5Kp1aAA3/bienlai/thu/" + a + "/" + timestamp.getTime() + "/status").setValue(1);
            mFirebaseDatabase.child("CuaHangOder/Meskv6p2bkf89ferNygy5Kp1aAA3/bienlai/thu/" + a + "/" + timestamp.getTime() + "/tongthanhtoan").setValue(40000);
            mFirebaseDatabase.child("CuaHangOder/Meskv6p2bkf89ferNygy5Kp1aAA3/bienlai/thu/" + a + "/" + timestamp.getTime() + "/tongtien").setValue(40000);
            dem++;
        }
    }

    private void setArray() {
        ArrayList<PieTongQuan> setArray = new ArrayList<>();
        setArray.add(new PieTongQuan("a", 0));
        if (sanPham.size() > 0) {
            for (int i = 0; i < sanPham.size(); i++) {
                for (int j = 0; j < setArray.size(); j++) {
                    if (sanPham.get(i).getName().equals(setArray.get(j).getName())) {

                        setArray.add(new PieTongQuan(sanPham.get(i).getName(), 0));
                    } else {
                        Log.d("ccc", sanPham.get(i).getName());
                    }
                }
            }
        }

        for (int i = 0; i < setArray.size(); i++) {

            for (int j = 0; j < sanPham.size(); j++) {
                if (setArray.get(i).getName().equals(sanPham.get(j).getName())) {
                    setArray.get(i).setSoLuong(setArray.get(i).getSoLuong() + sanPham.get(j).getSoLuong());
                }
            }
        }

        setArray.sort((o1, o2) -> o2.getSoLuong() - o1.getSoLuong());
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

    private ArrayList<DonHang> donHangs;
    private SupportFragmentDonOnline support = new SupportFragmentDonOnline();

    private void getDataFireBase() {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        mFirebaseDatabase.child("JxZOOK1RzcMM7pL5I6naGZfYSsu2/donhangonline/dondadat").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                donHangs = new ArrayList<>();
                listDays = MangNgay();
                int i = 0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String key = postSnapshot.getKey();
                    for (int j = 0; j < listDays.size(); j++) {
                        if (listDays.get(j).equals(key)) {
                            for (DataSnapshot snap : postSnapshot.getChildren()) {
                                DonHang donHang = snap.getValue(DonHang.class);
                                if (donHang.getTrangthai() == 6) {
                                    donHangs.add(donHang);
                                    Date date = support.formatDate(donHangs.get(i).getTime());
                                    donHangs.get(i).setDate(date);
                                    i++;
                                }
                            }
                            break;
                        }
                    }
                }
                support.SapXepDate(donHangs);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("FIREBASE", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}
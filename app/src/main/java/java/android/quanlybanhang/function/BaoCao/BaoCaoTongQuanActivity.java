package java.android.quanlybanhang.function.BaoCao;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.MainActivity;
import java.text.NumberFormat;
import java.util.Locale;

public class BaoCaoTongQuanActivity extends AppCompatActivity implements View.OnClickListener{

    private Locale localeVN = new Locale("vi", "VN");
    private NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);

    private TextView tien_tongtien, tien_nophaithu, tien_nophaitra, tien_doanhthu, tien_doanhso, tien_chuathanhtoan, tien_dathanhtoan, tien_dichvuphuthu, tien_comonhuy,
            tien_hoadononline,
            sl_doanhso, sl_chuathanhtoan, sl_dathanhtoan, sl_dichvuphuthu, sl_comonhuy, sl_hoadononline;
    private Button thoiGianLamViec, btnChiNhanh;
    private CardView cv_tongtien, cv_nophaithu, cv_nophaitra, cv_doanhthu, cv_doanhso, cv_chuathantoan, cv_dathanhtoan, cv_dicvuphuthu, cv_comonhuy, cv_hoadononline;
    private BottomSheetDialog bottomSheetDialog;
    private View sheetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_bao_cao_tong_quan);

        getIDLayout();

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

    //get id layout
    private void getIDLayout() {
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

        //textview soluong
        sl_doanhso = findViewById(R.id.sl_doanhso);
        sl_chuathanhtoan = findViewById(R.id.sl_chuathanhtoan);
        sl_dathanhtoan = findViewById(R.id.sl_dathanhtoan);
        sl_dichvuphuthu = findViewById(R.id.sl_dichvuphuthu);
        sl_comonhuy = findViewById(R.id.sl_comonhuy);
        sl_hoadononline = findViewById(R.id.sl_hoadononline);

        setOnclick();
    }

    //onlcik
    private void setOnclick(){
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
    }

    //get data
    private void getData() {

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
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
        switch (item.getItemId()){
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
}
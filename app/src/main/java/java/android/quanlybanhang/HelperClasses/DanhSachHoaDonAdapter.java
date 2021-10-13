package java.android.quanlybanhang.HelperClasses;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.android.quanlybanhang.Model.PieTongQuan;
import java.android.quanlybanhang.R;
import java.util.List;

public class DanhSachHoaDonAdapter extends RecyclerView.Adapter<DanhSachHoaDonAdapter.CustomChiSoSanPham> {

    private Context context;

    public DanhSachHoaDonAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CustomChiSoSanPham onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DanhSachHoaDonAdapter.CustomChiSoSanPham(LayoutInflater.from(context).inflate(R.layout.item_danh_sach_bien_lai, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomChiSoSanPham holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class CustomChiSoSanPham extends RecyclerView.ViewHolder {
//        private TextView title, soLuong;

        public CustomChiSoSanPham(@NonNull View ItemView) {
            super(ItemView);

        }
    }
}
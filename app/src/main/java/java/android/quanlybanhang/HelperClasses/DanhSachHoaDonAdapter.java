package java.android.quanlybanhang.HelperClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.android.quanlybanhang.R;

public class DanhSachHoaDonAdapter extends RecyclerView.Adapter<DanhSachHoaDonAdapter.CustomChiSoSanPham> {

    private Context context;

    public DanhSachHoaDonAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CustomChiSoSanPham onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomChiSoSanPham(LayoutInflater.from(context).inflate(R.layout.item_danh_sach_bien_lai, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomChiSoSanPham holder, int position) {
        if (position == 0) {
            holder.ngay.setText("13/10/2021");
            holder.gio.setText("10:30 AM");
            holder.gia.setText("240,000,00");
            holder.ten.setText("Hoang Huu Long");
        }else if (position == 1) {
            holder.ngay.setText("13/10/2021");
            holder.gio.setText("10:30 AM");
            holder.gia.setText("300,000,00");
            holder.ten.setText("Huu Long Hoang");
        }else if (position == 2) {
            holder.ngay.setText("12/10/2021");
            holder.gio.setText("09:30 AM");
            holder.gia.setText("100,000,00");
            holder.ten.setText("Long Hoang Huu");
        }else if (position == 3) {
            holder.ngay.setText("20/10/2021");
            holder.gio.setText("03:30 PM");
            holder.gia.setText("100,000,00");
            holder.ten.setText("Long Long Huu");
        }else if (position == 4) {
            holder.ngay.setText("30/09/2021");
            holder.gio.setText("01:30 AM");
            holder.gia.setText("90,000,00");
            holder.ten.setText("Long Hoang Huu");
        }
        else if (position == 5) {
            holder.ngay.setText("12/01/2021");
            holder.gio.setText("09:30 AM");
            holder.gia.setText("111,000,00");
            holder.ten.setText("Long Hoang Huu");
        }
    }

    @Override
    public int getItemCount() {
        return 30;
    }

    public class CustomChiSoSanPham extends RecyclerView.ViewHolder {
        private TextView ngay, gio, gia, ten;
        public CustomChiSoSanPham(@NonNull View ItemView) {
            super(ItemView);
            ngay = ItemView.findViewById(R.id.ngay);
            gio = ItemView.findViewById(R.id.gio);
            gia = ItemView.findViewById(R.id.gia);
            ten = ItemView.findViewById(R.id.ten);
        }
    }
}
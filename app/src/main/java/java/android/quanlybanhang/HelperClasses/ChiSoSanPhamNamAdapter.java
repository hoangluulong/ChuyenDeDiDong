package java.android.quanlybanhang.HelperClasses;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.android.quanlybanhang.Model.SanPhamTop;
import java.android.quanlybanhang.R;
import java.util.ArrayList;
import java.util.Locale;

public class ChiSoSanPhamNamAdapter extends RecyclerView.Adapter<ChiSoSanPhamNamAdapter.CustomChiSoSanPham2> {

    private Context context;
    private ArrayList<SanPhamTop> list;

    public ChiSoSanPhamNamAdapter(Context context, ArrayList<SanPhamTop> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ChiSoSanPhamNamAdapter.CustomChiSoSanPham2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChiSoSanPhamNamAdapter.CustomChiSoSanPham2(LayoutInflater.from(context).inflate(R.layout.item_chi_so_san_pham2, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChiSoSanPhamNamAdapter.CustomChiSoSanPham2 holder, int position) {
        holder.title.setText(list.get(position).getName() + "hoang huu long");
        holder.soLuong.setText(list.get(position).getSoLuong() + "0000");
        holder.gia.setText(formatStr(list.get(position).getGia()));
    }

    private String formatStr(double val) {
        return String.format(Locale.US, "%,.2f", val);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CustomChiSoSanPham2 extends RecyclerView.ViewHolder {
        private TextView title, soLuong, gia;

        public CustomChiSoSanPham2(@NonNull View ItemView) {
            super(ItemView);
            title = ItemView.findViewById(R.id.lblTitle);
            soLuong = ItemView.findViewById(R.id.lblSoLuong);
            gia = ItemView.findViewById(R.id.gia);
        }
    }
}

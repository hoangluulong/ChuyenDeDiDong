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

public class ChiSoSanPhamAdapter extends RecyclerView.Adapter<ChiSoSanPhamAdapter.CustomChiSoSanPham> {

    private Context context;
    private List<PieTongQuan> list;

    public ChiSoSanPhamAdapter(Context context, List<PieTongQuan> sanPham){
        this.context = context;
        this.list = sanPham;
    }

    @NonNull
    @Override
    public CustomChiSoSanPham onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomChiSoSanPham(LayoutInflater.from(context).inflate(R.layout.item_chi_so_san_pham, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomChiSoSanPham holder, int position) {
        holder.title.setText(list.get(position).getName());
        holder.soLuong.setText(list.get(position).getSoLuong()+"");
        holder.soLuong.setTextColor(Color.parseColor(list.get(position).getColor()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CustomChiSoSanPham extends RecyclerView.ViewHolder {
        private TextView title, soLuong;
        public CustomChiSoSanPham(@NonNull View ItemView) {
            super(ItemView);
            title = ItemView.findViewById(R.id.lblTitle);
            soLuong = ItemView.findViewById(R.id.lblSoLuong);
        }
    }
}



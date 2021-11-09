package java.android.quanlybanhang.HelperClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.android.quanlybanhang.Model.ChiTietNhapKho;
import java.android.quanlybanhang.R;
import java.util.List;

public class CapNhatBaoCaoKhoAdapter extends RecyclerView.Adapter<CapNhatBaoCaoKhoAdapter.anhXa> {

    private Context context;
    private List<ChiTietNhapKho> list;

    public CapNhatBaoCaoKhoAdapter(Context context, List<ChiTietNhapKho> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public anhXa onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new anhXa(LayoutInflater.from(context).inflate(R.layout.item_chi_tiet_cap_nhat_kho_activity, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull anhXa holder, int position) {
        ChiTietNhapKho chiTietNhapKho=list.get(position);
        holder.tenSanPham.setText(chiTietNhapKho.getTenSanPham());
        holder.soLieuCu.setText(chiTietNhapKho.getSoLieuCu());
        holder.soLieuMoi.setText(chiTietNhapKho.getSoLieuMoi());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class anhXa extends RecyclerView.ViewHolder{
        TextView tenSanPham,soLieuMoi,soLieuCu;
        public anhXa(@NonNull View itemView) {
            super(itemView);
            tenSanPham=itemView.findViewById(R.id.tenSanPham);
            soLieuCu=itemView.findViewById(R.id.soLieuMoi);
            soLieuMoi=itemView.findViewById(R.id.soLieuMoi);
        }
    }
}

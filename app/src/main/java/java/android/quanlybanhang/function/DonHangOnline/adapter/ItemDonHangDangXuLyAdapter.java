package java.android.quanlybanhang.function.DonHangOnline.adapter;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.android.quanlybanhang.Common.FormatDouble;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.DonHangOnline.data.DonHang;
import java.android.quanlybanhang.function.DonHangOnline.data.SanPham;
import java.util.ArrayList;

public class ItemDonHangDangXuLyAdapter extends RecyclerView.Adapter<ItemDonHangDangXuLyAdapter.DonCho> {

    private Dialog dialog;
    private ArrayList<SanPham> list;
    private FormatDouble formatDouble;

    public ItemDonHangDangXuLyAdapter(Dialog dialog, ArrayList<SanPham> list) {
        this.dialog = dialog;
        this.list = list;
        formatDouble = new FormatDouble();
    }

    @NonNull
    @Override
    public ItemDonHangDangXuLyAdapter.DonCho onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemDonHangDangXuLyAdapter.DonCho(LayoutInflater.from(dialog.getContext()).inflate(R.layout.item_dialog_don_online, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemDonHangDangXuLyAdapter.DonCho holder, int position) {
        holder.name.setText(list.get(position).getNameProduct());
        holder.dongia.setText(formatDouble.formatStr(list.get(position).getGiaProudct()));
        holder.soluong.setText(list.get(position).getSoluong()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DonCho extends RecyclerView.ViewHolder {
        private TextView name, loai, soluong, dongia;
        public DonCho(@NonNull View ItemView) {
            super(ItemView);
            name = ItemView.findViewById(R.id.name);
            loai = ItemView.findViewById(R.id.loai);
            soluong = ItemView.findViewById(R.id.soluong);
            dongia = ItemView.findViewById(R.id.dongia);
        }
    }
}
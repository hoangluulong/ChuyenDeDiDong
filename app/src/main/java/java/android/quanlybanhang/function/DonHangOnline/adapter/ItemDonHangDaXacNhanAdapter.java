package java.android.quanlybanhang.function.DonHangOnline.adapter;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.android.quanlybanhang.Common.FormatDouble;
import java.android.quanlybanhang.Common.SupportFragmentDonOnline;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.DonHangOnline.data.SanPham;
import java.util.ArrayList;

public class ItemDonHangDaXacNhanAdapter extends RecyclerView.Adapter<ItemDonHangDaXacNhanAdapter.DonCho> {
    private Dialog dialog;
    private ArrayList<SanPham> list;
    private FormatDouble formatDouble;
    private SupportFragmentDonOnline support;

    public ItemDonHangDaXacNhanAdapter(Dialog dialog, ArrayList<SanPham> list) {
        this.dialog = dialog;
        this.list = list;
        formatDouble = new FormatDouble();
        support = new SupportFragmentDonOnline();
    }

    @NonNull
    @Override
    public DonCho onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DonCho(LayoutInflater.from(dialog.getContext()).inflate(R.layout.item_dialog_don_online, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DonCho holder, int position) {
        holder.name.setText(list.get(position).getNameProduct());
        holder.dongia.setText(formatDouble.formatStr(list.get(position).getDonGia().get(0).getGiaBan()));
        holder.loai.setText(list.get(position).getDonGia().get(0).getTenDonGia());
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

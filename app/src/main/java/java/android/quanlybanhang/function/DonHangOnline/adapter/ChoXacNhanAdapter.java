package java.android.quanlybanhang.function.DonHangOnline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.android.quanlybanhang.R;
import java.util.ArrayList;

public class ChoXacNhanAdapter extends RecyclerView.Adapter<ChoXacNhanAdapter.DonChoXacNhan>{
    private Context context;
    private ArrayList<String> list;

    public ChoXacNhanAdapter (Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DonChoXacNhan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChoXacNhanAdapter.DonChoXacNhan(LayoutInflater.from(context).inflate(R.layout.item_danh_sach_don_hang, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DonChoXacNhan holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DonChoXacNhan extends RecyclerView.ViewHolder {
        private TextView lblThoiGian,lblDonGia, lblDiaChi, lblXacNhan, lblHuy;
        private LinearLayout layoutThongTin;
        public DonChoXacNhan(@NonNull View ItemView) {
            super(ItemView);
            lblThoiGian = ItemView.findViewById(R.id.lblThoiGian);
            lblDonGia = ItemView.findViewById(R.id.lblDonGia);
            lblDiaChi = ItemView.findViewById(R.id.lblDiaChi);
//            lblXacNhan = ItemView.findViewById(R.id.lblXacNhan);
//            lblHuy = ItemView.findViewById(R.id.lblHuy);
            layoutThongTin = ItemView.findViewById(R.id.layoutThongTin);
        }
    }
}

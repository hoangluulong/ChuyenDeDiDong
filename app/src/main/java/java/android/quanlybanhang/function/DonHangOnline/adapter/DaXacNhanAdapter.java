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

public class DaXacNhanAdapter extends RecyclerView.Adapter<DaXacNhanAdapter.DaXacNhan>{

    private Context context;
    private ArrayList<String> list;

    public DaXacNhanAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DaXacNhan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DaXacNhanAdapter.DaXacNhan(LayoutInflater.from(context).inflate(R.layout.item_don_hang_da_xac_nhan, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DaXacNhan holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class DaXacNhan extends RecyclerView.ViewHolder {
        private TextView lblThoiGian,lblDonGia, lblDiaChi, lblXacNhan, lblHuy;
        private LinearLayout layoutThongTin;
        public DaXacNhan(@NonNull View ItemView) {
            super(ItemView);
            lblThoiGian = ItemView.findViewById(R.id.lblThoiGian);
            lblDonGia = ItemView.findViewById(R.id.lblDonGia);
            lblDiaChi = ItemView.findViewById(R.id.lblDiaChi);
            layoutThongTin = ItemView.findViewById(R.id.layoutThongTin);
        }
    }
}

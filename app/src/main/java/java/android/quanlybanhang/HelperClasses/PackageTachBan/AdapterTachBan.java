package java.android.quanlybanhang.HelperClasses.PackageTachBan;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.android.quanlybanhang.Model.ChucNangThanhToan.ProductPushFB;
import java.android.quanlybanhang.Model.ChucNangThanhToan.ProuductPushFB1;
import java.android.quanlybanhang.R;
import java.util.ArrayList;

public class AdapterTachBan extends RecyclerView.Adapter<AdapterTachBan.TachBanHodel> {
    private ArrayList<ProuductPushFB1> items;

    public AdapterTachBan(ArrayList<ProuductPushFB1> items){
        this.items = items;
    }
    @NonNull
    @Override
    public TachBanHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tachban,parent,false);
        AdapterTachBan.TachBanHodel tachBanHodel = new AdapterTachBan.TachBanHodel(view);
        return tachBanHodel;
    }

    @Override
    public void onBindViewHolder(@NonNull TachBanHodel holder, int position) {
        ProuductPushFB1 crr = items.get(position);
        Log.d("khabanh1",items.size()+"");
        holder.tvtensanpham.setText(crr.getNameProduct());
        holder.tvsoluong.setText(crr.getSoluong()+"");
        holder.tvgiasanpham.setText(crr.getGiaProudct()+"");
        holder.tvLoai.setText(crr.getLoai());

    }

    @Override
    public int getItemCount() {
        if (items!=null){
            return items.size() ;

        }
        return 0;
    }

    public class TachBanHodel extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView tvtensanpham,tvLoai,tvgiasanpham,Tong1SanPham,tvsoluong;
        ImageView imgminus,imgplus;
        public TachBanHodel(@NonNull View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.checkBox);
            tvtensanpham=itemView.findViewById(R.id.tvtensanpham);
            tvLoai=itemView.findViewById(R.id.tvLoai);
            tvgiasanpham=itemView.findViewById(R.id.tvgiasanpham);
            Tong1SanPham=itemView.findViewById(R.id.Tong1SanPham);
            imgminus=itemView.findViewById(R.id.imgminus);
            imgplus=itemView.findViewById(R.id.imgplus);
            tvsoluong=itemView.findViewById(R.id.tvsoluong);


        }
    }
}

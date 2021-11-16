package java.android.quanlybanhang.HelperClasses.PackageTachBan;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.android.quanlybanhang.Common.ArrayListTachBan;
import java.android.quanlybanhang.Model.ChucNangThanhToan.ProductPushFB;
import java.android.quanlybanhang.Model.ChucNangThanhToan.ProuductPushFB1;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.TachBanActivity;
import java.util.ArrayList;

public class AdapterTachBan extends RecyclerView.Adapter<AdapterTachBan.TachBanHodel> {
    private ArrayList<ProductPushFB> items;

    ArrayList<ProuductPushFB1> itemtach;
    int select;
    ArrayListTachBan  arrayListTachBan;
    TachBanActivity tachBanActivity;
    public AdapterTachBan(ArrayList<ProductPushFB> items,TachBanActivity tachBanActivity,ArrayListTachBan  arrayListTachBan){
        this.items = items;
        this.tachBanActivity = tachBanActivity;
        this.arrayListTachBan = arrayListTachBan;
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
        itemtach = new ArrayList<ProuductPushFB1>();
        ProductPushFB crr = items.get(0);

        if(items.size()>0 && items!=null){
            int a =crr.getSanpham().get(position).getSoluong();
            Log.d("dddd",a+"");
                holder.tvtensanpham.setText(crr.getSanpham().get(position).getNameProduct());
                holder.tvsoluong.setText(crr.getSanpham().get(position).getSoluong()+"");
                holder.tvgiasanpham.setText(crr.getSanpham().get(position).getGiaProudct()+"");
                holder.tvLoai.setText(crr.getSanpham().get(position).getLoai());
            holder.imgminus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.tvsoluong.setText(crr.getSanpham().get(position).getSoluong()+"");
                    if(crr.getSanpham().get(position).getSoluong()>0){
                        items.get(0).getSanpham().get(position).setSoluong(items.get(0).getSanpham().get(position).getSoluong()-1);
                        Log.d("dddd",items.get(0).getSanpham().get(position).getSoluong()+""+"");
                        holder.tvsoluong.setText(items.get(0).getSanpham().get(position).getSoluong()+"");
                        if(items.get(0).getSanpham().get(position).getSoluong()<1){
                            itemtach.remove(items.get(0).getSanpham().get(position));
                            holder.checkBox.setChecked(false);

                        }
                    }
                }
            });
            holder.imgplus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.tvsoluong.setText( items.get(0).getSanpham().get(position).getSoluong()+"");

                    if( items.get(0).getSanpham().get(position).getSoluong()<=a-1){
                        items.get(0).getSanpham().get(position).setSoluong(items.get(0).getSanpham().get(position).getSoluong()+1);
                        holder.tvsoluong.setText(crr.getSanpham().get(position).getSoluong()+"");
                    }
                }
            });

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.checkBox.isChecked()){
                        holder.tvsoluong.setText( items.get(0).getSanpham().get(position).getSoluong()+"");
                        if(items.get(0).getSanpham().get(position).getSoluong()==0){
                            holder.checkBox.setChecked(false);
                            itemtach.remove(items.get(0).getSanpham().get(position));
                        }
                        else {
                            holder.tvsoluong.setText( items.get(0).getSanpham().get(position).getSoluong()+"");
                        itemtach.add(items.get(0).getSanpham().get(position));
                        }
                    }
                    else {
                        itemtach.remove(items.get(0).getSanpham().get(position));
                    }
                    Log.d("itemtachs",itemtach.size()+"");
                    arrayListTachBan.arrTachBan(itemtach);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (items!=null){
            return items.get(0).getSanpham().size() ;
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

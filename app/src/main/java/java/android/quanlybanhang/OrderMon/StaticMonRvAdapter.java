package java.android.quanlybanhang.OrderMon;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.android.quanlybanhang.CategoryMon.StaticCategoryMonModel;
import java.android.quanlybanhang.ChiTietSanPham.ChiTietSanPham;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.MonOrder;
import java.util.ArrayList;

public class StaticMonRvAdapter  extends RecyclerView.Adapter<StaticMonRvAdapter.StaticMonRvViewHolder>{
ArrayList<Product> staticMonOrderModels;
    public MonOrder monOrder;
//    public IclickGetMon iclickGetMon;

//    public interface IclickGetMon{
//        void clickItent(Product product );
//    }
//
//    public void setData(IclickGetMon iclickGetMon){
//        this.iclickGetMon = iclickGetMon;
//    }

    ArrayList<StaticCategoryMonModel> items;
    int pos;

    String id_khuvuc;
    String id_ban;
    String tenban;


    public StaticMonRvAdapter(ArrayList<Product> staticMonOrderModels, MonOrder monOrder, ArrayList<StaticCategoryMonModel> items, int pos, String tenban,String id_ban,String id_khuvuc){
        this.staticMonOrderModels = staticMonOrderModels;
        this.monOrder = monOrder;
        this.items = items;
        this.pos = pos;
        this.tenban = tenban;
        this.id_ban = id_ban;
        this.id_khuvuc = id_khuvuc;
    }
    @NonNull
    @Override
    public StaticMonRvAdapter.StaticMonRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanpham,parent,false);

        StaticMonRvAdapter.StaticMonRvViewHolder staticMonRvViewHolder = new StaticMonRvAdapter.StaticMonRvViewHolder(view);

        return staticMonRvViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull StaticMonRvAdapter.StaticMonRvViewHolder holder, int position) {
        Product Crritem = staticMonOrderModels.get(position);
        if(Crritem==null){
            return;
        }
        holder.tvtensanpham.setText(Crritem.getNameProduct());
//        Log.d("aa",tenban.toString()+"kuku");
//        holder.imgsanpham(Crritem.getImgProduct());
        Picasso.get().load(Crritem.getImgProduct()).into(holder.imgsanpham);

        holder.tvgiaSanpham.setText(Crritem.getGiaBan()+"");
//        holder.tvgiaSanpham.setText(staticMonOrderModels.get(position).getDonGia().get(0)+"");
        holder.tvstatus.setText(Crritem.getStatus());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent1 = new Intent(monOrder, ChiTietSanPham.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("sp",Crritem);
                    intent1.putExtras(bundle);
                    Log.d("aaa",id_ban+"baba1");
                    intent1.putExtra("tenban",tenban);
                    intent1.putExtra("id_ban",id_ban);
                    intent1.putExtra("id_khuvuc",id_khuvuc);

                    monOrder.startActivity(intent1);
//                    startActivityForResult(intent1,1);

            }
        });

    }

    @Override
    public int getItemCount() {
        return staticMonOrderModels.size();
    }


    public class StaticMonRvViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgsanpham;
        public TextView tvtensanpham;
        public TextView tvgiaSanpham;
        public  TextView tvstatus;
        ConstraintLayout constraintLayout;
        public StaticMonRvViewHolder(@NonNull View itemView) {

            super(itemView);
            imgsanpham = itemView.findViewById(R.id.imgsanpham);
            tvtensanpham = itemView.findViewById(R.id.tvtensanpham);
            tvgiaSanpham = itemView.findViewById(R.id.tvgiasanpham);
            tvstatus = itemView.findViewById(R.id.tvstatus);
            constraintLayout = itemView.findViewById(R.id.constraintLayouts);

        }
    }
}

package java.android.quanlybanhang.function.CuaHangOnline.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.CuaHangOnline.Data.SanPhamQuangCao;
import java.android.quanlybanhang.function.CuaHangOnline.Fragment.ChoXacNhanQuangCaoFragment;
import java.util.ArrayList;

public class SanPhamQuangCaoAdapter extends RecyclerView.Adapter<SanPhamQuangCaoAdapter.SanPhamHodel>{

    private Context context;
    private ArrayList<SanPhamQuangCao> list;
    private ChoXacNhanQuangCaoFragment choXacNhanQuangCaoFragment;

    public SanPhamQuangCaoAdapter(Context context, ArrayList<SanPhamQuangCao> list, ChoXacNhanQuangCaoFragment choXacNhanQuangCaoFragment) {
        this.context = context;
        this.list = list;
        this.choXacNhanQuangCaoFragment = choXacNhanQuangCaoFragment;
    }

    @NonNull
    @Override
    public SanPhamHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SanPhamQuangCaoAdapter.SanPhamHodel(LayoutInflater.from(context).inflate(R.layout.item_san_pham_dang_quang_cao, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamHodel holder, int position) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.linerlayoutEnabled.getLayoutParams();
        if (list.get(position).isUp()) {
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            holder.updownIMG.setImageResource(R.drawable.up_24);
            holder.linerlayoutEnabled.setLayoutParams(params);
        } else {
            params.height = 0;
            holder.updownIMG.setImageResource(R.drawable.down_24);
            holder.linerlayoutEnabled.setLayoutParams(params);
        }

        Picasso.get().load(list.get(position).getImageUrl()).into(holder.image);
        holder.ngaybatdau.setText(list.get(position).getNgayBatDau());
        holder.ngayketthuc.setText(list.get(position).getNgayKetThuc());
        holder.nhomsanpham.setText(list.get(position).getNhomSp());
        holder.giaban.setText(list.get(position).getGiaBan()+"");
        holder.giamgia.setText(list.get(position).getGiamGia()+"");
        holder.name.setText(list.get(position).getTenSanPham());
        holder.idsanpham.setText(list.get(position).getIdCuahHang());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SanPhamHodel extends RecyclerView.ViewHolder {

        private TextView ngaybatdau, ngayketthuc, nhomsanpham, giaban, giamgia, huy, name,idsanpham;
        private ImageView updownIMG, image;
        private LinearLayout itemClick, linerlayoutEnabled;
        private LinearLayout.LayoutParams params;

        public SanPhamHodel(@NonNull View ItemView) {
            super(ItemView);
            ngaybatdau = ItemView.findViewById(R.id.ngaybatdau);
            ngayketthuc = ItemView.findViewById(R.id.ngayketthuc);
            nhomsanpham = ItemView.findViewById(R.id.nhomsanpham);
            giaban = ItemView.findViewById(R.id.giaban);
            giamgia = ItemView.findViewById(R.id.giamgia);
            huy = ItemView.findViewById(R.id.huy);
            name = ItemView.findViewById(R.id.name);
            idsanpham = ItemView.findViewById(R.id.idsanpham);
            updownIMG = ItemView.findViewById(R.id.updownIMG);
            image = ItemView.findViewById(R.id.image);
            itemClick = ItemView.findViewById(R.id.itemClick);
            linerlayoutEnabled = ItemView.findViewById(R.id.linerlayoutEnabled);
            params= (LinearLayout.LayoutParams) linerlayoutEnabled.getLayoutParams();


            huy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    choXacNhanQuangCaoFragment.delete(position);
                }
            });

            itemClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    if (list.get(position).isUp()) {
                        params.height = 0;
                        list.get(position).setUp(false);
                        updownIMG.setImageResource(R.drawable.down_24);
                        linerlayoutEnabled.setLayoutParams(params);
                        notifyDataSetChanged();
                    } else {
                        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        list.get(position).setUp(true);
                        updownIMG.setImageResource(R.drawable.up_24);
                        linerlayoutEnabled.setLayoutParams(params);
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }
}

package java.android.quanlybanhang.HelperClasses.Package_AdapterSanPham;



import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;


import java.android.quanlybanhang.HelperClasses.Pakage_AdapterKhuVuc.StaticModelKhuVuc;
import java.android.quanlybanhang.Model.Product;
import java.android.quanlybanhang.Model.SanPham.DonViTinh;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.SanPham.ListDonViTinh;
import java.android.quanlybanhang.function.SanPham.ListProduct;
import java.android.quanlybanhang.function.SanPham.SuaSanPhamActivity;
import java.android.quanlybanhang.function.ThemBan_KhuVuc.SuaKhuVuc;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterDonViTinh extends RecyclerView.Adapter<AdapterDonViTinh.DonViTinhViewHolder> {
    private ArrayList<DonViTinh> arrayList;
    private ListDonViTinh context;
    private Context context2;



    public AdapterDonViTinh( ListDonViTinh context,Context context2,ArrayList<DonViTinh> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
        this.context2 = context2;
    }

    @NonNull
    @Override
    public DonViTinhViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donvitinh,parent,false);
        DonViTinhViewHolder productViewHolder = new DonViTinhViewHolder(view);
        return productViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull DonViTinhViewHolder holder, int position) {
        DonViTinh donViTinh = arrayList.get(position);
        String name = donViTinh.getDonViTinh().toUpperCase();
        char nam =name.charAt(0) ;
        holder.textViewName.setText(donViTinh.getDonViTinh());
        holder.nameViewName1.setText(nam+"");

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class DonViTinhViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName;
        private TextView nameViewName1;
        private ImageView imgXoa;
        private ImageView imgSua;

        public DonViTinhViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textnameDVT);
            nameViewName1 = itemView.findViewById(R.id.textTenDVT);
            imgXoa = itemView.findViewById(R.id.btnXoaDVT);
            imgSua = itemView.findViewById(R.id.btnSuaDVT);
            imgXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    context.delete(position);
                }
            });

            imgSua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    context.update(Gravity.CENTER,position);
                }
            });

        }
    }


}



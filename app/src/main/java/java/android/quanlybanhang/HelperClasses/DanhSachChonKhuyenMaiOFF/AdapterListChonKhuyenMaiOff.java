package java.android.quanlybanhang.HelperClasses.DanhSachChonKhuyenMaiOFF;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;


import java.android.quanlybanhang.HelperClasses.Pakage_AdapterKhuVuc.StaticModelKhuVuc;
import java.android.quanlybanhang.Model.KhuyenMaiOffModel;
import java.android.quanlybanhang.Model.ListKhuyenMaiOffModel;
import java.android.quanlybanhang.Model.Product;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.KhuyenMaiOffLine.ListChonKhuyenMaiOff;
import java.android.quanlybanhang.function.SanPham.ListProduct;
import java.android.quanlybanhang.function.SanPham.SuaSanPhamActivity;
import java.android.quanlybanhang.function.ThemBan_KhuVuc.SuaKhuVuc;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterListChonKhuyenMaiOff extends RecyclerView.Adapter<AdapterListChonKhuyenMaiOff.ListChonKhuyeMaiViewHolder> {
    private  ArrayList<KhuyenMaiOffModel>  arrayList;
    private ListChonKhuyenMaiOff context;




    public AdapterListChonKhuyenMaiOff( ListChonKhuyenMaiOff context,ArrayList<KhuyenMaiOffModel> arrayList) {
        this.arrayList = arrayList;
        this.context = context;

    }

    @NonNull
    @Override
    public ListChonKhuyeMaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danhsachend_khuyenmaioff1,parent,false);
        ListChonKhuyeMaiViewHolder productViewHolder = new ListChonKhuyeMaiViewHolder(view);
        return productViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ListChonKhuyeMaiViewHolder holder, int position) {
        KhuyenMaiOffModel crr = arrayList.get(position);
        holder.giatu.setText(crr.getGiakhuyenmaitu());
        holder.giaden.setText(crr.getGiakhuyenmaiden());
        holder.giakhuyenmai.setText(crr.getGiakhuyenmai());
        int k = position;


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ListChonKhuyeMaiViewHolder extends RecyclerView.ViewHolder {

        TextView giatu,giaden,giakhuyenmai,tv_namesale;
        ConstraintLayout constraintLayout;
        ImageView xoa;

        public ListChonKhuyeMaiViewHolder(@NonNull View itemView) {
            super(itemView);
            giatu = itemView.findViewById(R.id.giatu);
            giaden = itemView.findViewById(R.id.giaden);
            giakhuyenmai = itemView.findViewById(R.id.giakhuyenmai);
            constraintLayout =itemView.findViewById(R.id.constraintLayout3);
            xoa = itemView.findViewById(R.id.bnt_delete);

            xoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    context.delete(position, new ListChonKhuyenMaiOff.delete1() {
                        @Override
                        public void delete() {
                            notifyDataSetChanged();
                        }
                    });

                }
            });

        }
    }
}



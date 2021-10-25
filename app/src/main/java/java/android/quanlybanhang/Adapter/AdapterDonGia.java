package java.android.quanlybanhang.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.android.quanlybanhang.Data.DonGia;
import java.android.quanlybanhang.Data.DonViTinh;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.SanPham.AddProduct;
import java.android.quanlybanhang.function.SanPham.SuaSanPhamActivity;
import java.util.ArrayList;


public class AdapterDonGia extends RecyclerView.Adapter<AdapterDonGia.AdapterDonGiaHolder> {
    ArrayList<DonGia> donGias;
    Context context1;
    private View customLayout1;
    private AlertDialog.Builder builder1;
    private LayoutInflater inflater;



    public AdapterDonGia(Context context1,ArrayList<DonGia> donGias){
        this.donGias = donGias;
        this.context1 = context1;
    }
    public AdapterDonGia(Context context1, ArrayList<DonGia> donGias, LayoutInflater inflater,AlertDialog.Builder builder1,View customLayout1 ){
        this.donGias = donGias;
        this.context1 = context1;
        this.inflater = inflater;
       this.builder1 = builder1;
       this.customLayout1 = customLayout1;
    }

    @NonNull
    @Override
    public AdapterDonGia.AdapterDonGiaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_itemlistdongia,parent,false);
        AdapterDonGiaHolder staticRvViewHolder = new AdapterDonGiaHolder(view);
        return staticRvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDonGia.AdapterDonGiaHolder holder, int position) {
        DonGia donGia = donGias.get(position);
        holder.textView.setText(donGia.getTenDonGia());
        holder.textViewGia.setText(donGia.getGiaBan()+"");

        holder.Xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donGias.remove(position);
                notifyDataSetChanged();
                Log.d("dongia",donGias.size()+"");

            }
        });

        holder.Sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                builder1.setTitle("Thêm đơn vị tính");
                builder1.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder1.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                });
                builder1.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return donGias.size();
    }

    public class AdapterDonGiaHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textViewGia;
        ImageView Xoa, Sua;

        public AdapterDonGiaHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.lblName);
            textViewGia = itemView.findViewById(R.id.lbGia);
            Xoa = itemView.findViewById(R.id.XoaDonVi);
            Sua = itemView.findViewById(R.id.UpdateDonVi);

            Xoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    donGias.remove(position);
                }
            });
        }
    }
}

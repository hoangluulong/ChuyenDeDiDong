package java.android.quanlybanhang.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.android.quanlybanhang.Data.DonGia;
import java.android.quanlybanhang.R;
import java.util.ArrayList;


public class AdapterDonGia extends RecyclerView.Adapter<AdapterDonGia.AdapterDonGiaHolder> {
    ArrayList<DonGia> donGias;
    public AdapterDonGia(ArrayList<DonGia> donGias){
        this.donGias = donGias;
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

    }

    @Override
    public int getItemCount() {
        return donGias.size();
    }

    public class AdapterDonGiaHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textViewGia;

        public AdapterDonGiaHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.lblName);
            textViewGia = itemView.findViewById(R.id.lbGia);
        }
    }
}

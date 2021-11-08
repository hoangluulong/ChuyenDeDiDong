package java.android.quanlybanhang.HelperClasses.Package_AdapterKhachHang;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.android.quanlybanhang.Model.ChucNangThanhToan.DonGia;
import java.android.quanlybanhang.Model.KhachHang.KhachHang;
import java.android.quanlybanhang.Model.KhachHang.NhomKhachHang;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.KhachHang.SuaKhachHang;
import java.android.quanlybanhang.function.SanPham.SuaSanPhamActivity;
import java.util.ArrayList;


public class AdapterNhomKhachHang extends RecyclerView.Adapter<AdapterNhomKhachHang.AdapterNhomKhachHangHolder> {
    ArrayList<NhomKhachHang> nhomKhachHangs;
    Context context;

    public AdapterNhomKhachHang(Context context,ArrayList<NhomKhachHang> nhomKhachHangs){
        this.nhomKhachHangs =nhomKhachHangs;
        this.context = context;
    }


    @NonNull
    @Override
    public AdapterNhomKhachHangHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nhomkhachhang,parent,false);
        AdapterNhomKhachHangHolder staticRvViewHolder = new AdapterNhomKhachHangHolder(view);
        return staticRvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNhomKhachHangHolder holder, int position) {
        NhomKhachHang nhomKhachHang = nhomKhachHangs.get(position);
        String name = nhomKhachHang.getTenNhomKh().toUpperCase();
        char nam =name.charAt(0) ;
        holder.textViewTen1.setText(nam+"");
        holder.textViewTen.setText(nhomKhachHang.getTenNhomKh());
    }

    @Override
    public int getItemCount() {
        return nhomKhachHangs.size();
    }

    public class AdapterNhomKhachHangHolder extends RecyclerView.ViewHolder {
        TextView textViewTen;
        TextView textViewTen1;
        ImageView imgXoa;
        ImageView imgSua;
        public AdapterNhomKhachHangHolder(@NonNull View itemView) {
            super(itemView);
            textViewTen = itemView.findViewById(R.id.textnameNKH);
            textViewTen1 = itemView.findViewById(R.id.textTen);
            imgXoa = itemView.findViewById(R.id.btnXoaNKH);
            imgSua = itemView.findViewById(R.id.btnSuaNKH);

            imgSua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }



    }
}

package java.android.quanlybanhang.HelperClasses.Package_AdapterKhachHang;

import android.app.Dialog;
import android.content.Context;
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
import java.android.quanlybanhang.R;
import java.util.ArrayList;


public class AdapterKhachHang extends RecyclerView.Adapter<AdapterKhachHang.AdapterKhachHangHolder> {
    ArrayList<KhachHang> khachHangs;
    Context context;



    public AdapterKhachHang(Context context,ArrayList<KhachHang> khachHangs){
       this.khachHangs =khachHangs;
        this.context = context;
    }


    @NonNull
    @Override
    public AdapterKhachHangHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_khachhang,parent,false);
        AdapterKhachHangHolder staticRvViewHolder = new AdapterKhachHangHolder(view);
        return staticRvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterKhachHangHolder holder, int position) {
        KhachHang khachHang = khachHangs.get(position);
        holder.textViewTen.setText(khachHang.getHoTenKhachHang());
        holder.textViewSDT.setText(khachHang.getSoDT());
    }

    @Override
    public int getItemCount() {
        return khachHangs.size();
    }

    public class AdapterKhachHangHolder extends RecyclerView.ViewHolder {
        TextView textViewTen;
        TextView textViewSDT;
        ImageView imgXoa;
        ImageView imgSua;
        public AdapterKhachHangHolder(@NonNull View itemView) {
            super(itemView);
            textViewTen = itemView.findViewById(R.id.edtextTenKhachHang);
            textViewSDT = itemView.findViewById(R.id.edtextSDTKhachHang);
            imgXoa = itemView.findViewById(R.id.btnXoaKhachHang);
            imgSua = itemView.findViewById(R.id.btnSuaKhachHang);
        }


    }
}

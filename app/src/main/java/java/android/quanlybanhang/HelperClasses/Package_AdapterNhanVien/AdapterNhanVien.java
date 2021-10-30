package java.android.quanlybanhang.HelperClasses.Package_AdapterNhanVien;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.android.quanlybanhang.Model.NhanVien_CaLam.NhanVien;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.NhanVien.ListNhanVien;
import java.util.ArrayList;


public class AdapterNhanVien extends RecyclerView.Adapter<AdapterNhanVien.NhanVienViewHolder> {
    private ArrayList<NhanVien> arrayList;
    private ListNhanVien context;

    public AdapterNhanVien(ListNhanVien context, ArrayList<NhanVien> arrayList){
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NhanVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listnhanvien,parent,false);
        NhanVienViewHolder nhanVienViewHolder = new NhanVienViewHolder(view);
        return nhanVienViewHolder ;
    }

    @Override
    public void onBindViewHolder(@NonNull NhanVienViewHolder holder, int position) {
        NhanVien nhanVien = arrayList.get(position);
        holder.textViewName.setText(nhanVien.getUsername());
        holder.textViewSDT.setText(nhanVien.getPhone());
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class NhanVienViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName;
        private TextView textViewSDT;

        public NhanVienViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.edtextTenNhanVien);
            textViewSDT = itemView.findViewById(R.id.edtextSDT);
        }
    }
}

package java.android.quanlybanhang.HelperClasses.Package_AdapterNhanVien;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.android.quanlybanhang.Model.NhanVien_CaLam.NhanVien;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.NhanVien.ActivityUpdateNhanVien;
import java.android.quanlybanhang.function.NhanVien.ListNhanVien;
import java.util.ArrayList;


public class AdapterNhanVien extends RecyclerView.Adapter<AdapterNhanVien.NhanVienViewHolder> {
    private ArrayList<NhanVien> arrayList;
    private ListNhanVien context;
    private Context context1;

    public AdapterNhanVien(Context context1,ListNhanVien context, ArrayList<NhanVien> arrayList){
        this.arrayList = arrayList;
        this.context = context;
        this.context1 = context1;
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
        private ImageView imgSua;
        private ImageView imgXoa;


        public NhanVienViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.edtextTenNhanVien);
            textViewSDT = itemView.findViewById(R.id.edtextSDT);
            imgXoa = itemView.findViewById(R.id.btnXoaNV);
            imgSua = itemView.findViewById(R.id.btnSuaNV);

            imgSua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    Intent intent = new Intent(context1, ActivityUpdateNhanVien.class);
                    intent.putExtra("Key_arrayNV", arrayList.get(position));
                    context1.startActivity(intent);
                }
            });

            imgXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    context.delete(position);
                }
            });
        }
    }
}

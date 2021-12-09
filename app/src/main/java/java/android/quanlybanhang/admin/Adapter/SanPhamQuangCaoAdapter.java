package java.android.quanlybanhang.admin.Adapter;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.CuaHangOnline.Data.Product;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class SanPhamQuangCaoAdapter extends RecyclerView.Adapter<SanPhamQuangCaoAdapter.HolderSanPham> {
    private ArrayList<Product> listQC;
    private DatabaseReference mDatabase;
    private int loai;

    public SanPhamQuangCaoAdapter(ArrayList<Product> listQC, int loai) {
        this.listQC = listQC;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        this.loai = loai;
    }

    public void setLoai(int loai) {
        this.loai = loai;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HolderSanPham onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_sanpham_quang_cao,parent,false);
        SanPhamQuangCaoAdapter.HolderSanPham staticRvViewHolder = new SanPhamQuangCaoAdapter.HolderSanPham(view);
        return staticRvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderSanPham holder, int position) {
        Picasso.get().load(listQC.get(position).getImgProduct()).into(holder.image_sp);
        holder.tv_ten_cua_hang.setText(listQC.get(position).getName());
        holder.tv_id_sp_quan_cao.setText(listQC.get(position).getId());
        holder.tv_so_dien_thoai.setText(listQC.get(position).getSoDienThoai());
        holder.tv_ten_san_pham.setText(listQC.get(position).getNameProduct());
        holder.tv_so_luong.setText(listQC.get(position).getSoluong()+"");


        if (loai == 2) {
            holder.btn_huy.setVisibility(View.VISIBLE);
            holder.btn_duyet.setEnabled(false);
        }

        holder.btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.btn_duyet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "HHHHHHHHH", Toast.LENGTH_SHORT).show();
                listQC.get(position).setNgayBatDau(hamlaydate());
                listQC.get(position).setNgayKetThuc(congNgay());
                mDatabase.child("sanPhamQuangCao").child(listQC.get(position).getIdCuaHang()).child("sanpham").child(listQC.get(position).getId()).setValue(listQC.get(position)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        mDatabase.child("ChoXacNhan").child(listQC.get(position).getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(v.getContext(), "Xác nhận thành công!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return listQC.size();
    }

    public class HolderSanPham extends RecyclerView.ViewHolder {
        private TextView tv_id_sp_quan_cao, tv_ten_cua_hang, tv_so_dien_thoai, tv_ten_san_pham, tv_so_luong, btn_huy, btn_duyet;
        private ImageView image_sp;
        public HolderSanPham(@NonNull View itemView) {
            super(itemView);
            image_sp = itemView.findViewById(R.id.image_sp);
            tv_id_sp_quan_cao = itemView.findViewById(R.id.tv_id_sp_quan_cao);
            tv_ten_cua_hang = itemView.findViewById(R.id.tv_ten_cua_hang);
            tv_so_dien_thoai = itemView.findViewById(R.id.tv_so_dien_thoai);
            tv_ten_san_pham = itemView.findViewById(R.id.tv_ten_san_pham);
            tv_so_luong = itemView.findViewById(R.id.tv_so_luong);
            btn_huy = itemView.findViewById(R.id.btn_huy);
            btn_duyet = itemView.findViewById(R.id.btn_duyet);
        }
    }

    private String hamlaydate() {
        String date = new SimpleDateFormat("dd-MM-YYYY", Locale.getDefault()).format(new java.util.Date());
        return date;
    }

    private String congNgay() {
        Calendar c1 = Calendar.getInstance();
        String datetr = new SimpleDateFormat("YYYY-MM-dd", Locale.getDefault()).format(new java.util.Date());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = Date.valueOf(datetr);
        c1.setTime(date);

        c1.roll(Calendar.DATE, 10);
        return dateFormat.format(c1.getTime());
    }
}

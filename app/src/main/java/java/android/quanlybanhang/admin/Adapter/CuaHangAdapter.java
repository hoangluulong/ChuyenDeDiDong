package java.android.quanlybanhang.admin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.android.quanlybanhang.R;
import java.android.quanlybanhang.admin.data.CuaHang;
import java.android.quanlybanhang.function.CuaHangOnline.Data.Product;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class CuaHangAdapter extends RecyclerView.Adapter<CuaHangAdapter.HolderSanPham> {
    private DatabaseReference mDatabase;
    private int loai;
    private ArrayList<CuaHang> cuaHangs;
    private Context context;

    public CuaHangAdapter(Context context ,ArrayList<CuaHang> cuaHangs, int loai) {
        this.cuaHangs = cuaHangs;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        this.loai = loai;
        this.context = context;
    }

    public void setLoai(int loai) {
        this.loai = loai;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HolderSanPham onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_khach_hang,parent,false);
        CuaHangAdapter.HolderSanPham staticRvViewHolder = new CuaHangAdapter.HolderSanPham(view);
        return staticRvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderSanPham holder, int position) {

        holder.tv_id_cua_hang.setText(cuaHangs.get(position).getID_CUAHANG());
        holder.tv_ten_cua_hang.setText(cuaHangs.get(position).getTenCuaHang());
        holder.tv_so_dien_thoai.setText(cuaHangs.get(position).getSoDienThoai());

        if (loai == 1) {
            holder.btn_khoa.setTextColor(ContextCompat.getColor(context, R.color.red));
            holder.btn_khoa.setText("Khóa");
        }else {
            holder.btn_khoa.setTextColor(ContextCompat.getColor(context, R.color.xanh));
            holder.btn_khoa.setText("Mở");
        }


        holder.btn_khoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loai == 1) {
                    mDatabase.child("CuaHangOder").child(cuaHangs.get(position).getID_CUAHANG()).child("khoa").setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            holder.btn_khoa.setText("Đã khóa");
                            holder.btn_khoa.setEnabled(true);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    cuaHangs.remove(position);
                                    notifyDataSetChanged();
                                }
                            }, 1000);
                            Toast.makeText(context, "Đã khóa!", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else {
                    mDatabase.child("CuaHangOder").child(cuaHangs.get(position).getID_CUAHANG()).child("khoa").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            holder.btn_khoa.setText("Đã mở");
                            holder.btn_khoa.setEnabled(true);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    cuaHangs.remove(position);
                                    notifyDataSetChanged();
                                }
                            }, 1000);
                            Toast.makeText(context, "Đã Mở khóa!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cuaHangs.size();
    }

    public class HolderSanPham extends RecyclerView.ViewHolder {

        private TextView btn_khoa,tv_id_cua_hang, tv_ten_cua_hang, tv_so_dien_thoai;

        public HolderSanPham(@NonNull View itemView) {
            super(itemView);

            btn_khoa = itemView.findViewById(R.id.btn_huy);
            tv_id_cua_hang = itemView.findViewById(R.id.tv_id_cua_hang);
            tv_so_dien_thoai = itemView.findViewById(R.id.tv_so_dien_thoai);
            tv_ten_cua_hang = itemView.findViewById(R.id.tv_ten_cua_hang);
        }
    }
}

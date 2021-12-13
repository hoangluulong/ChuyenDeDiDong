package java.android.quanlybanhang.function.DonHangOnline.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.android.quanlybanhang.Common.FormatDouble;
import java.android.quanlybanhang.Common.SupportFragmentDonOnline;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.DonHangOnline.data.DonHang;
import java.android.quanlybanhang.function.DonHangOnline.data.SanPham;
import java.util.ArrayList;

public class DonHangHuyAdapter extends RecyclerView.Adapter<DonHangHuyAdapter.DonChoXacNhan>{
    private Context context;
    private ArrayList<DonHang> list;
    private ItemDonHangDangGiaoAdapter itemDonHangAdapter;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    private FormatDouble formatDouble;
    private SupportFragmentDonOnline support;


    public DonHangHuyAdapter(Context context, ArrayList<DonHang> list) {
        this.context = context;
        this.list = list;
        formatDouble = new FormatDouble();
        support = new SupportFragmentDonOnline();
    }

    @NonNull
    @Override
    public DonChoXacNhan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DonHangHuyAdapter.DonChoXacNhan(LayoutInflater.from(context).inflate(R.layout.item_dang_xu_ly_do_online, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DonChoXacNhan holder, int position) {
        if (list.get(position).getTrangthai() == 7) {
            holder.trangthaidonhang.setText("Đơn hàng bị hủy");
            holder.trangthaidonhang.setTextColor(ContextCompat.getColor(context, R.color.color_one));
        }
         if(list.get(position).getTrangthai() == 8){
            holder.trangthaidonhang.setText("Đơn hàng hủy");
             holder.trangthaidonhang.setTextColor(ContextCompat.getColor(context, R.color.color_one));
        }

        holder.nguoiThucHien.setText("Ship: "+ list.get(position).getShipper());
        holder.lblThoiGian.setText(support.formartDate(list.get(position).getDate()));
        holder.lblDonGia.setText(formatDouble.formatStr(list.get(position).getDonGia() - list.get(position).getThunhap()));
        holder.lblKhachang.setText(list.get(position).getTenKhachhang());
        holder.lblDiaChi.setText(list.get(position).getDiaChi());
        holder.tv_id_donhang.setText(list.get(position).getIdDonHang());
        holder.nguoiThucHien.setText("NV: "+list.get(position).getNhanVien());

        holder.layoutThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFeedbackDialog(Gravity.CENTER, position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DonChoXacNhan extends RecyclerView.ViewHolder {
        private TextView trangthaidonhang, nguoiThucHien, lblDonGia, lblThoiGian, lblKhachang, lblDiaChi, tv_id_donhang;
        private LinearLayout layoutThongTin;
        public DonChoXacNhan(@NonNull View ItemView) {
            super(ItemView);
            trangthaidonhang = ItemView.findViewById(R.id.trangthaidonhang);
            nguoiThucHien = ItemView.findViewById(R.id.nguoiThucHien);
            layoutThongTin = ItemView.findViewById(R.id.layoutThongTin);
            lblDonGia = ItemView.findViewById(R.id.lblDonGia);
            lblThoiGian = ItemView.findViewById(R.id.lblThoiGian);
            lblKhachang = ItemView.findViewById(R.id.lblKhachang);
            lblDiaChi = ItemView.findViewById(R.id.lblDiaChi);
            tv_id_donhang = ItemView.findViewById(R.id.tv_id_donhang);
        }
    }

    private void openFeedbackDialog(int gravity, int position) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_don_hang_da_xac_nhan);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tenkhachhang = dialog.findViewById(R.id.tenkhachhang);
        TextView diachi = dialog.findViewById(R.id.diachi);
        TextView khuyenmai = dialog.findViewById(R.id.khuyenmai);
        RecyclerView recycleview = dialog.findViewById(R.id.recycleview);
        TextView tongtien = dialog.findViewById(R.id.tongtien);
        ImageView close = dialog.findViewById(R.id.close);
        TextView thanhTien = dialog.findViewById(R.id.thanhTien);
        TextView thoigian = dialog.findViewById(R.id.thoigian);

        thoigian.setText(support.formartDate(list.get(position).getDate()));
        tenkhachhang.setText(list.get(position).getTenKhachhang());
        diachi.setText(list.get(position).getDiaChi());
        tongtien.setText(formatDouble.formatStr(list.get(position).getDonGia() - list.get(position).getThunhap() + list.get(position).getGiaKhuyenMai()));
        khuyenmai.setText(formatDouble.formatStr(list.get(position).getGiaKhuyenMai()));
        thanhTien.setText(formatDouble.formatStr(list.get(position).getDonGia() - list.get(position).getThunhap()));


        displayItem(recycleview, dialog, list.get(position).getSanpham());

        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void displayItem(RecyclerView recyclerView , Dialog dialog, ArrayList<SanPham> sanPhams){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(dialog.getContext(), 1));

        itemDonHangAdapter = new ItemDonHangDangGiaoAdapter(dialog, sanPhams);
        recyclerView.setAdapter(itemDonHangAdapter);

        itemDonHangAdapter.notifyDataSetChanged();
    }
}

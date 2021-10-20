package java.android.quanlybanhang.function.DonHangOnline.adapter;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.android.quanlybanhang.Common.FormatDouble;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.DonHangOnline.data.DonHang;
import java.android.quanlybanhang.function.DonHangOnline.data.SanPham;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DangXuLiAdapter extends RecyclerView.Adapter<DangXuLiAdapter.DonHangXuLy>{

    private Context context;
    private ArrayList<DonHang> list;
    private Dialog dialog;
    private ItemDonHangDangXuLyAdapter itemDonHangAdapter;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    private FormatDouble formatDouble;

    public DangXuLiAdapter(Context context, ArrayList<DonHang> list) {
        this.context = context;
        this.list = list;
        formatDouble = new FormatDouble();
    }

    @NonNull
    @Override
    public DangXuLiAdapter.DonHangXuLy onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DangXuLiAdapter.DonHangXuLy(LayoutInflater.from(context).inflate(R.layout.item_dang_xu_ly_do_online, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DangXuLiAdapter.DonHangXuLy holder, int position) {
        holder.trangthaidonhang.setText("Đang giao");
        holder.nguoiThucHien.setText("Ship: "+ list.get(position).getShipper());
        holder.lblThoiGian.setText(formartDate(list.get(position).getDate()));
        holder.lblDonGia.setText(formatDouble.formatStr(TinhTongTien(list.get(position).getSanpham()) - list.get(position).getGiaKhuyenMai()));
        holder.lblKhachang.setText(list.get(position).getTenKhachHang());
        holder.lblDiaChi.setText(list.get(position).getDiaChi());

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

    public class DonHangXuLy extends RecyclerView.ViewHolder {
        private TextView trangthaidonhang, nguoiThucHien, lblDonGia, lblThoiGian, thoigian_denhientai, lblKhachang, lblDiaChi;
        private LinearLayout layoutThongTin;
        public DonHangXuLy(@NonNull View ItemView) {
            super(ItemView);
            trangthaidonhang = ItemView.findViewById(R.id.trangthaidonhang);
            nguoiThucHien = ItemView.findViewById(R.id.nguoiThucHien);
            layoutThongTin = ItemView.findViewById(R.id.layoutThongTin);
            lblDonGia = ItemView.findViewById(R.id.lblDonGia);
            lblThoiGian = ItemView.findViewById(R.id.lblThoiGian);
            thoigian_denhientai = ItemView.findViewById(R.id.thoigian_denhientai);
            lblKhachang = ItemView.findViewById(R.id.lblKhachang);
            lblDiaChi = ItemView.findViewById(R.id.lblDiaChi);
        }
    }

    private void openFeedbackDialog(int gravity, int position) {
        dialog = new Dialog(context);
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

        tenkhachhang.setText(list.get(position).getTenKhachHang());
        diachi.setText(list.get(position).getDiaChi());
        tongtien.setText(formatDouble.formatStr(TinhTongTien(list.get(position).getSanpham())));
        khuyenmai.setText(formatDouble.formatStr(list.get(position).getGiaKhuyenMai()));
        thanhTien.setText(formatDouble.formatStr((TinhTongTien(list.get(position).getSanpham())) - list.get(position).getTrangthai()));


        displayItem(recycleview, dialog, position);

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

    private void displayItem(RecyclerView recyclerView , Dialog dialog, int position){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(dialog.getContext(), 1));

        itemDonHangAdapter = new ItemDonHangDangXuLyAdapter(dialog, list.get(position).getSanpham());
        recyclerView.setAdapter(itemDonHangAdapter);

        itemDonHangAdapter.notifyDataSetChanged();
    }

    private String formartDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy HH:mm");
        String dt = formatter.format(date);

        return dt;
    }

    private Double TinhTongTien(ArrayList<SanPham> sanPhams) {

        double tongGia = 0;
        for (int i = 0; i < sanPhams.size(); i++) {
            tongGia += sanPhams.get(i).getGiaProudct();
        }
        return tongGia;
    }
}

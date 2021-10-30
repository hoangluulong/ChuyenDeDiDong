package java.android.quanlybanhang.function.BepBar.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.BepBar.DangXuLyActivity;
import java.android.quanlybanhang.function.BepBar.Data.SanPhamOder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DonHangOfflineAdapter extends RecyclerView.Adapter<DonHangOfflineAdapter.ViewHolderBan> {
    public DonHangOfflineAdapter(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
        this.coLor = 0;
    }

    public void setData(List<SanPhamOder> list) {
        this.tableList = list;
        notifyDataSetChanged();
    }

    int coLor;
    private DatabaseReference mDatabase;
    Activity activity;
    Context context;
    List<SanPhamOder> tableList;


    @Override
    public ViewHolderBan onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View heroView = inflater.inflate(R.layout.item_bep_bar_danh_sanh_ban, parent, false);
        return new ViewHolderBan(heroView);
    }

    @Override
    public void onBindViewHolder(DonHangOfflineAdapter.ViewHolderBan holder, int position) {
        SanPhamOder table = tableList.get(position);
        if (KhuVucBan(position).length > 1) {
            holder.examName.setText("Bàn " + KhuVucBan(position)[0]);
            holder.khuvuc.setText(" - Khu vực " + KhuVucBan(position)[1]);
        }else {
            holder.examName.setText("ID: " + KhuVucBan(position)[0]);
            holder.khuvuc.setText("");
        }

        holder.examDate.setText(table.getDate() + "");
        holder.xuly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DangXuLyActivity.class);
                intent.putExtra("key", table.getNameTable());
                context.startActivity(intent);
            }
        });

        holder.soluong.setText(soLuong(table)+"");
        holder.examDate.setText(changeDate(table.getDate()+""));
        holder.tongtien.setText(TongTien(table)+"");
        holder.trangThai.setText("Chờ sử lý");
    }

    @Override
    public int getItemCount() {
        return tableList.size();
    }


    public class ViewHolderBan extends RecyclerView.ViewHolder {
        private TextView examName;
        private TextView examDate;
        private TextView khuvuc;
        private TextView xuly;
        private TextView soluong,tongtien, trangThai;

        public ViewHolderBan(View itemView) {
            super(itemView);
            examName = itemView.findViewById(R.id.examName);
            examDate = itemView.findViewById(R.id.examDate);
            khuvuc = itemView.findViewById(R.id.khuvuc);
            xuly = itemView.findViewById(R.id.xuly);
            soluong = itemView.findViewById(R.id.soluong);
            tongtien = itemView.findViewById(R.id.tongtien);
            trangThai = itemView.findViewById(R.id.trangThai);
        }

    }

    private String[] KhuVucBan(int position) {
        String[] parts = tableList.get(position).getNameTable().split("_");
        return  parts;
    }

    private int soLuong(SanPhamOder sanPhamOder) {
        int soLuong = 0;
        double tien = 0;
        for (int i = 0; i < sanPhamOder.getSanpham().size(); i++){
            soLuong += sanPhamOder.getSanpham().get(i).getSoluong();
        }

        return soLuong;
    }

    private double TongTien(SanPhamOder sanPhamOder) {
        double tien = 0;
        for (int i = 0; i < sanPhamOder.getSanpham().size(); i++){
            tien += (sanPhamOder.getSanpham().get(i).getSoluong() * sanPhamOder.getSanpham().get(i).getGiaProudct());
        }

        return tien;
    }

    public String changeDate(String date){
        long dates = Long.parseLong(date);
        java.sql.Timestamp timestamp = new java.sql.Timestamp(dates);
        if(dates ==0){
            return "";
        }
        Date date1 =new Date(timestamp.getTime());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-YYYY hh:mm:ss");
        String aaa = simpleDateFormat.format(date1);
        return  aaa;

    }

}

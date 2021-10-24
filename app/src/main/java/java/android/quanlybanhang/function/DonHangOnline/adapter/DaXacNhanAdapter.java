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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.android.quanlybanhang.Common.FormatDouble;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.DonHangOnline.data.DonHang;
import java.android.quanlybanhang.function.DonHangOnline.data.SanPham;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DaXacNhanAdapter extends RecyclerView.Adapter<DaXacNhanAdapter.DaXacNhan>{

    private Context context;
    private ArrayList<DonHang> list;
    private Dialog dialog;
    private ItemDonHangDaXacNhanAdapter itemDonHangAdapter;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    private FormatDouble formatDouble;
    private int select;


    public DaXacNhanAdapter(Context context, ArrayList<DonHang> list, Dialog dialog) {
        this.context = context;
        this.list = list;
        this.dialog = dialog;
        this.select = 0;
        formatDouble = new FormatDouble();
    }

    @NonNull
    @Override
    public DaXacNhan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DaXacNhanAdapter.DaXacNhan(LayoutInflater.from(context).inflate(R.layout.item_don_hang_da_xac_nhan, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DaXacNhan holder, int position) {
        holder.layoutThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFeedbackDialog(Gravity.CENTER, position);
            }
        });


        holder.lblHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFeedbackDialogHuy(Gravity.CENTER, position);
            }
        });

        holder.lblHangCho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFirebaseXacNhanDonHang(list.get(position).getKey(), position);
            }
        });

        holder.lblThoiGian.setText(formartDate(list.get(position).getDate()));
        holder.lblKhachang.setText(list.get(position).getTenKhachhang());
        holder.lblDonGia.setText(formatDouble.formatStr(list.get(position).getDonGia()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DaXacNhan extends RecyclerView.ViewHolder {
        private TextView lblThoiGian,lblDonGia, lblXacNhan, lblHuy, lblHangCho, lblKhachang;
        private LinearLayout layoutThongTin;
        public DaXacNhan(@NonNull View ItemView) {
            super(ItemView);
            lblThoiGian = ItemView.findViewById(R.id.lblThoiGian);
            lblDonGia = ItemView.findViewById(R.id.lblDonGia);
            lblHuy = ItemView.findViewById(R.id.lblhuy);
            layoutThongTin = ItemView.findViewById(R.id.layoutThongTin);
            lblHangCho = ItemView.findViewById(R.id.lblHangCho);
            lblKhachang = ItemView.findViewById(R.id.lblKhachang);
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
        RecyclerView recycleview = dialog.findViewById(R.id.recycleview);
        TextView tongtien = dialog.findViewById(R.id.tongtien);
        ImageView close = dialog.findViewById(R.id.close);

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

    private void openFeedbackDialogHuy(int gravity, int positon) {
        Dialog dialogHuy = new Dialog(context);
        dialogHuy.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogHuy.setContentView(R.layout.dialog_huy_don);

        Window window = dialogHuy.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btn_dong = dialogHuy.findViewById(R.id.btn_dong);
        Button btn_huy = dialogHuy.findViewById(R.id.btn_huy);

        if (Gravity.BOTTOM == gravity) {
            dialogHuy.setCancelable(true);
        } else {
            dialogHuy.setCancelable(false);
        }

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFirebaseHuyDonDonHang(list.get(positon).getKey());
                dialogHuy.dismiss();
            }
        });

        btn_dong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogHuy.dismiss();
            }
        });
        dialogHuy.show();
    }

    private void displayItem(RecyclerView recyclerView , Dialog dialog, int position){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(dialog.getContext(), 1));

        itemDonHangAdapter = new ItemDonHangDaXacNhanAdapter(dialog, list.get(position).getSanpham());
        recyclerView.setAdapter(itemDonHangAdapter);

        itemDonHangAdapter.notifyDataSetChanged();
    }

    //TODO: setDuLieu Firebase xác nhận
    private void setFirebaseXacNhanDonHang (String IdDonHang, int position) {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        mFirebaseDatabase.child("JxZOOK1RzcMM7pL5I6naGZfYSsu2/donhangonline/dondadat/"+IdDonHang+"/trangthai").setValue(0).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Đã xác nhận đơn", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Xác nhận đơn không thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //TODO: setDuLieu Firebase hủy đơn
    private void setFirebaseHuyDonDonHang (String IdDonHang) {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        mFirebaseDatabase.child("JxZOOK1RzcMM7pL5I6naGZfYSsu2/donhangonline/dondadat/"+IdDonHang).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Đã hủy đơn", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Hủy thất bại", Toast.LENGTH_SHORT).show();
            }
        });
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

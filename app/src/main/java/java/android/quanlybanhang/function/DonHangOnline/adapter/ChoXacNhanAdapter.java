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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChoXacNhanAdapter extends RecyclerView.Adapter<ChoXacNhanAdapter.DonChoXacNhan>{
    private Context context;
    private ArrayList<DonHang> list;
    private Dialog dialog;
    private ItemDonHangAdapter itemDonHangAdapter;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    private int select;
    private Dialog dialogHuy;
    private FormatDouble formatDouble;
    private SupportFragmentDonOnline support;


    public ChoXacNhanAdapter (Context context, ArrayList<DonHang> list, Dialog dialog, Dialog dialogHuy) {
        this.context = context;
        this.list = list;
        this.dialog = dialog;
        this.select = 0;
        this.dialogHuy = dialogHuy;
        formatDouble = new FormatDouble();
        support = new SupportFragmentDonOnline();
    }

    @NonNull
    @Override
    public DonChoXacNhan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DonChoXacNhan(LayoutInflater.from(context).inflate(R.layout.item_don_hang_cho_xac_nhan, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DonChoXacNhan holder, int position) {
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

        if (list.get(position).getPhuongThucThanhToan() == 0){
            holder.thanhtoan.setText("Thanh toán khi nhận");
        }else {
            holder.thanhtoan.setText("Chuyển khoản");
        }
        holder.lblThoiGian.setText(support.formartDate(list.get(position).getDate()));
        holder.lblDiaChi.setText(list.get(position).getDiaChi());
        holder.lblKhachang.setText(list.get(position).getTenKhachhang());
        holder.lblDonGia.setText(formatDouble.formatStr(support.TinhTongTien(list.get(position).getSanpham()) - list.get(position).getGiaKhuyenMai()));
        holder.lblXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select = position;
                setFirebaseXacNhanDonHang(list.get(select).getKey(), select);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DonChoXacNhan extends RecyclerView.ViewHolder {
        private TextView lblThoiGian,lblDonGia, lblDiaChi, lblXacNhan, lblHuy, lblKhachang, thanhtoan;
        private LinearLayout layoutThongTin;
        public DonChoXacNhan(@NonNull View ItemView) {
            super(ItemView);
            lblThoiGian = ItemView.findViewById(R.id.lblThoiGian);
            lblDonGia = ItemView.findViewById(R.id.lblDonGia);
            lblDiaChi = ItemView.findViewById(R.id.lblDiaChi);
            lblXacNhan = ItemView.findViewById(R.id.lblXacNhan);
            lblHuy = ItemView.findViewById(R.id.lblhuy);
            layoutThongTin = ItemView.findViewById(R.id.layoutThongTin);
            lblKhachang = ItemView.findViewById(R.id.lblKhachang);
            thanhtoan = ItemView.findViewById(R.id.thanhtoan);
        }
    }

    private void openFeedbackDialog(int gravity, int position) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_don_hang_online);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tenkhachhang = dialog.findViewById(R.id.tenkhachhang);
        TextView diachi = dialog.findViewById(R.id.diachi);
        RecyclerView recycleview = dialog.findViewById(R.id.recycleview);
        TextView tongtien = dialog.findViewById(R.id.tongtien);
        Button btn_huy = dialog.findViewById(R.id.btn_huy);
        Button btn_xac_nhan = dialog.findViewById(R.id.btn_xac_nhan);
        ImageView close = dialog.findViewById(R.id.close);
        TextView khuyenmai = dialog.findViewById(R.id.khuyenmai);
        TextView thanhTien = dialog.findViewById(R.id.thanhTien);

        tongtien.setText(formatDouble.formatStr(support.TinhTongTien(list.get(position).getSanpham())));
        khuyenmai.setText(formatDouble.formatStr(list.get(position).getGiaKhuyenMai()));
        thanhTien.setText(formatDouble.formatStr(support.TinhTongTien(list.get(position).getSanpham()) - list.get(position).getGiaKhuyenMai()));

        displayItem(recycleview, dialog, position);
        Log.d("www", list.get(position).getSanpham().size()+"");
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

        btn_xac_nhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFirebaseXacNhanDonHang(list.get(position).getKey(), position);
                dialog.dismiss();
            }
        });

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void openFeedbackDialogHuy(int gravity, int positon) {
        dialogHuy = new Dialog(context);
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
                setFirebaseHuyDonDonHang(list.get(positon).getKey(), positon);
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

        itemDonHangAdapter = new ItemDonHangAdapter(dialog, list.get(position).getSanpham());
        recyclerView.setAdapter(itemDonHangAdapter);

        itemDonHangAdapter.notifyDataSetChanged();
    }

    //TODO: getFirebase Khách hàng
    private void getFirebaseKhachHang (String IDKhachHang) {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();

        mFirebaseDatabase.child("IDKhachHang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("FIREBASE", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }

    //TODO: setDuLieu Firebase xác nhận
    private void setFirebaseXacNhanDonHang (String IdDonHang, int position) {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        String key = mFirebaseDatabase.push().getKey();
        Log.d("abc", support.ngayHientai(list.get(position).getDate()));
        mFirebaseDatabase.child("JxZOOK1RzcMM7pL5I6naGZfYSsu2/donhangonline/dondadat/"+support.ngayHientai(list.get(position).getDate())+"/"+IdDonHang+"/trangthai").setValue(1).addOnSuccessListener(new OnSuccessListener<Void>() {
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

        DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference();
        list.get(position).setIdDonHang(key);
        mFirebaseDatabase.child("DonHangOnline/DaDatDon/"+ list.get(position).getIdKhachhang() + "/" +key).setValue(list.get(position));
    }

    //TODO: setDuLieu Firebase hủy đơn
    private void setFirebaseHuyDonDonHang (String IdDonHang, int position) {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        mFirebaseDatabase.child("JxZOOK1RzcMM7pL5I6naGZfYSsu2/donhangonline/dondadat/"+support.ngayHientai(list.get(position).getDate())+"/"+IdDonHang).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
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
}

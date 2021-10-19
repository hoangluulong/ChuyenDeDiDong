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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.DonHangOnline.data.DonHang;
import java.util.ArrayList;

public class ChoXacNhanAdapter extends RecyclerView.Adapter<ChoXacNhanAdapter.DonChoXacNhan>{
    private Context context;
    private ArrayList<DonHang> list;
    private Dialog dialog;
    private ItemDonHangAdapter itemDonHangAdapter;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;
    private int select;

    public ChoXacNhanAdapter (Context context, ArrayList<DonHang> list, Dialog dialog) {
        this.context = context;
        this.list = list;
        this.dialog = dialog;
        this.select = 0;

        for (int i = 0; i< list.size(); i++) {
            if (list.get(i).getTrangthai() != 0){
                Log.d("bbb", list.get(i).getTrangthai()+"");
                list.remove(i);
                notifyDataSetChanged();
            }
        }
    }

    @NonNull
    @Override
    public DonChoXacNhan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChoXacNhanAdapter.DonChoXacNhan(LayoutInflater.from(context).inflate(R.layout.item_don_hang_cho_xac_nhan, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DonChoXacNhan holder, int position) {

        holder.layoutThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Infor", Toast.LENGTH_SHORT).show();
                openFeedbackDialog(Gravity.CENTER);
            }
        });

        holder.lblThoiGian.setText(list.get(position).getTime());
        holder.lblDiaChi.setText(list.get(position).getDiaChi());
        holder.lblXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select = position;
                setFirebaseXacNhanDonHang(list.get(select).getKey(), select);
                Toast.makeText(context, "onclik", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DonChoXacNhan extends RecyclerView.ViewHolder {
        private TextView lblThoiGian,lblDonGia, lblDiaChi, lblXacNhan, lblHuy;
        private LinearLayout layoutThongTin;
        public DonChoXacNhan(@NonNull View ItemView) {
            super(ItemView);
            lblThoiGian = ItemView.findViewById(R.id.lblThoiGian);
            lblDonGia = ItemView.findViewById(R.id.lblDonGia);
            lblDiaChi = ItemView.findViewById(R.id.lblDiaChi);
            lblXacNhan = ItemView.findViewById(R.id.lblXacNhan);
            lblHuy = ItemView.findViewById(R.id.lblhuy);
            layoutThongTin = ItemView.findViewById(R.id.layoutThongTin);
        }
    }

    private void openFeedbackDialog(int gravity) {
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

        displayItem(recycleview, dialog);

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

    private void displayItem(RecyclerView recyclerView , Dialog dialog){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(dialog.getContext(), 1));
        ArrayList<String> arrayList = new ArrayList<>();

        itemDonHangAdapter = new ItemDonHangAdapter(dialog, arrayList);
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
        list.remove(position);
    }

    //TODO: setDuLieu Firebase hủy đơn
    private void setFirebaseHuyDonDonHang (String IdDonHang) {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        mFirebaseDatabase.child("JxZOOK1RzcMM7pL5I6naGZfYSsu2/donhangonline/dondadat/"+IdDonHang).removeValue();
    }

}

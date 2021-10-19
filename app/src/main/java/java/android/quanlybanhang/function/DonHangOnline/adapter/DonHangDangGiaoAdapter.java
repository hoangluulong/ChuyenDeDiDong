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

import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.DonHangOnline.data.DonHang;
import java.util.ArrayList;

public class DonHangDangGiaoAdapter extends RecyclerView.Adapter<DonHangDangGiaoAdapter.DonHangXuLy>{
    private Context context;
    private ArrayList<DonHang> list;
    private Dialog dialog;
    private ItemDonHangDangGiaoAdapter itemDonHangAdapter;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference mFirebaseDatabase;

    public DonHangDangGiaoAdapter(Context context, ArrayList<DonHang> list, Dialog dialog) {
        this.context = context;
        this.list = list;
        this.dialog = dialog;
    }
    @NonNull
    @Override
    public DonHangXuLy onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DonHangDangGiaoAdapter.DonHangXuLy(LayoutInflater.from(context).inflate(R.layout.item_dang_xu_ly_do_online, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangXuLy holder, int position) {
        holder.trangthaidonhang.setText("Đang giao");
        holder.nguoiThucHien.setText("Ship: Nguyễn Văn A");

        holder.layoutThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFeedbackDialog(Gravity.CENTER);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class DonHangXuLy extends RecyclerView.ViewHolder {
        private TextView trangthaidonhang, nguoiThucHien;
        private LinearLayout layoutThongTin;
        public DonHangXuLy(@NonNull View ItemView) {
            super(ItemView);
            trangthaidonhang = ItemView.findViewById(R.id.trangthaidonhang);
            nguoiThucHien = ItemView.findViewById(R.id.nguoiThucHien);
            layoutThongTin = ItemView.findViewById(R.id.layoutThongTin);
        }
    }

    private void openFeedbackDialog(int gravity) {
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

        dialog.show();
    }

    private void displayItem(RecyclerView recyclerView , Dialog dialog){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(dialog.getContext(), 1));
        ArrayList<String> arrayList = new ArrayList<>();

        itemDonHangAdapter = new ItemDonHangDangGiaoAdapter(dialog, arrayList);
        recyclerView.setAdapter(itemDonHangAdapter);

        itemDonHangAdapter.notifyDataSetChanged();
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
}

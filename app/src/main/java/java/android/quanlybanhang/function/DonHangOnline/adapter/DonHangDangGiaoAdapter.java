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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.android.quanlybanhang.R;
import java.util.ArrayList;

public class DonHangDangGiaoAdapter extends RecyclerView.Adapter<DonHangDangGiaoAdapter.DonHangXuLy>{
    private Context context;
    private ArrayList<String> list;
    private Dialog dialog;
    private ItemDonHangDangGiaoAdapter itemDonHangAdapter;

    public DonHangDangGiaoAdapter(Context context, ArrayList<String> list, Dialog dialog) {
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
}

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

import java.android.quanlybanhang.R;
import java.util.ArrayList;

public class DangXuLiAdapter extends RecyclerView.Adapter<DangXuLiAdapter.DonHangXuLy>{

    private Context context;
    private ArrayList<String> list;
    private Dialog dialog;
    private ItemDonHangDangXuLyAdapter itemDonHangAdapter;

    public DangXuLiAdapter(Context context, ArrayList<String> list, Dialog dialog) {
        this.context = context;
        this.list = list;
        this.dialog = dialog;
    }

    @NonNull
    @Override
    public DangXuLiAdapter.DonHangXuLy onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DangXuLiAdapter.DonHangXuLy(LayoutInflater.from(context).inflate(R.layout.item_dang_xu_ly_do_online, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DangXuLiAdapter.DonHangXuLy holder, int position) {
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
        private LinearLayout layoutThongTin;
        public DonHangXuLy(@NonNull View ItemView) {
            super(ItemView);
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

        itemDonHangAdapter = new ItemDonHangDangXuLyAdapter(dialog, arrayList);
        recyclerView.setAdapter(itemDonHangAdapter);

        itemDonHangAdapter.notifyDataSetChanged();
    }
}

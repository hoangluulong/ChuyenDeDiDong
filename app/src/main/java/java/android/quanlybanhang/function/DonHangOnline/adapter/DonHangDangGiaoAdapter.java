package java.android.quanlybanhang.function.DonHangOnline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.android.quanlybanhang.R;
import java.util.ArrayList;

public class DonHangDangGiaoAdapter extends RecyclerView.Adapter<DonHangDangGiaoAdapter.DonHangXuLy>{
    private Context context;
    private ArrayList<String> list;

    public DonHangDangGiaoAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public DonHangXuLy onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DonHangDangGiaoAdapter.DonHangXuLy(LayoutInflater.from(context).inflate(R.layout.item_dang_xu_ly_do_online, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangXuLy holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class DonHangXuLy extends RecyclerView.ViewHolder {
        public DonHangXuLy(@NonNull View ItemView) {
            super(ItemView);

        }
    }
}

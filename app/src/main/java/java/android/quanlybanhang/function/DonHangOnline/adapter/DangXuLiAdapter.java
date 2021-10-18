package java.android.quanlybanhang.function.DonHangOnline.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.android.quanlybanhang.R;
import java.util.ArrayList;

public class DangXuLiAdapter extends RecyclerView.Adapter<DangXuLiAdapter.DonHangXuLy>{

    private Context context;
    private ArrayList<String> list;

    public DangXuLiAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DangXuLiAdapter.DonHangXuLy onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DangXuLiAdapter.DonHangXuLy(LayoutInflater.from(context).inflate(R.layout.item_dang_xu_ly_do_online, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DangXuLiAdapter.DonHangXuLy holder, int position) {

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

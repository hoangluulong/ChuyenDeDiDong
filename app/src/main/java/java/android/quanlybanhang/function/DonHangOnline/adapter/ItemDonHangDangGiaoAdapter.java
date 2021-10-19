package java.android.quanlybanhang.function.DonHangOnline.adapter;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.android.quanlybanhang.R;
import java.util.ArrayList;

public class ItemDonHangDangGiaoAdapter extends RecyclerView.Adapter<ItemDonHangDangGiaoAdapter.DonCho> {

    private Dialog dialog;
    private ArrayList<String> list;

    public ItemDonHangDangGiaoAdapter(Dialog dialog, ArrayList<String> list) {
        this.dialog = dialog;
        this.list = list;
    }

    @NonNull
    @Override
    public DonCho onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemDonHangDangGiaoAdapter.DonCho(LayoutInflater.from(dialog.getContext()).inflate(R.layout.item_dialog_don_online, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DonCho holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class DonCho extends RecyclerView.ViewHolder {

        public DonCho(@NonNull View ItemView) {
            super(ItemView);

        }
    }
}

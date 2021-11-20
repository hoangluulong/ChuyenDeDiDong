package java.android.quanlybanhang.HelperClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.android.quanlybanhang.R;

public class ChiTietNhapKhoAdapter extends RecyclerView.Adapter<CustomChiTietNhapKho> {

    private Context context;
//    private List<SanPhamTop> list;

//    public ChiTietNhapKhoAdapter(Context context, List<SanPhamTop> list) {
//        this.context = context;
//        this.list = list;
//    }

    public ChiTietNhapKhoAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public CustomChiTietNhapKho onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomChiTietNhapKho(LayoutInflater.from(context).inflate(R.layout.item_chi_tiet_cap_nhat_kho, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomChiTietNhapKho holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 30;
    }
}

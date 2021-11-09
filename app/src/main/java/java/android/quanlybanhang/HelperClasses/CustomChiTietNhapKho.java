package java.android.quanlybanhang.HelperClasses;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.android.quanlybanhang.R;

public class CustomChiTietNhapKho extends RecyclerView.ViewHolder {
    CardView cardView;
    TextView tensanPham, solieuCu, solieuMoi;
    ImageView edit, delete;



    public CustomChiTietNhapKho(@NonNull View itemView) {
        super(itemView);

        cardView = itemView.findViewById(R.id.recylerView_chi_tiet_cap_nhat_kho);
        tensanPham = itemView.findViewById(R.id.tenSanPham);
        solieuCu = itemView.findViewById(R.id.soLieuCu);
        solieuMoi = itemView.findViewById(R.id.soLieuMoi);
        edit = itemView.findViewById(R.id.edit_item);
        delete = itemView.findViewById(R.id.delete_item);

    }
}

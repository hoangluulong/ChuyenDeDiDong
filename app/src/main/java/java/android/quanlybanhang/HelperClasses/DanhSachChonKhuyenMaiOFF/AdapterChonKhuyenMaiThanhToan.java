package java.android.quanlybanhang.HelperClasses.DanhSachChonKhuyenMaiOFF;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.android.quanlybanhang.Model.KhuyenMaiOffModel;
import java.android.quanlybanhang.Model.ListKhuyenMaiOffModel;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.ThanhToanActivity;
import java.util.ArrayList;

public class AdapterChonKhuyenMaiThanhToan extends RecyclerView.Adapter<AdapterChonKhuyenMaiThanhToan.AdapterChonkmhodel> {
    ArrayList<ListKhuyenMaiOffModel> listKhuyenMaiOffModels;
    ThanhToanActivity thanhToanActivity;

    public AdapterChonKhuyenMaiThanhToan(ArrayList<ListKhuyenMaiOffModel> listKhuyenMaiOffModels,ThanhToanActivity thanhToanActivity) {
        this.listKhuyenMaiOffModels = listKhuyenMaiOffModels;
        this.thanhToanActivity = thanhToanActivity;
    }

    @NonNull
    @Override
    public AdapterChonkmhodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_khuyenmai_in_dialogthanhtoan, parent, false);
        AdapterChonKhuyenMaiThanhToan.AdapterChonkmhodel adapterChonKhuyenMai = new AdapterChonKhuyenMaiThanhToan.AdapterChonkmhodel(view);
        return adapterChonKhuyenMai;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterChonkmhodel holder, int position) {
        ListKhuyenMaiOffModel crr = listKhuyenMaiOffModels.get(position);
        holder.giatu.setText(crr.getNgaybatdau());
        holder.giaden.setText(crr.getNgayketthuc());
        holder.giakhuyenmai.setText(crr.getNhomkhachhang());
        holder.tv_namesale.setText(crr.getTenkhuyenmai());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(thanhToanActivity, "Onclick", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listKhuyenMaiOffModels != null) {
            return
                    listKhuyenMaiOffModels.size();
        }
        return 0;
    }

    public class AdapterChonkmhodel extends RecyclerView.ViewHolder {
        TextView giatu,giaden,giakhuyenmai,tv_namesale;
        ConstraintLayout constraintLayout;
        public AdapterChonkmhodel(@NonNull View itemView) {
            super(itemView);
            giatu = itemView.findViewById(R.id.giatu);
            giaden = itemView.findViewById(R.id.giaden);
            giakhuyenmai = itemView.findViewById(R.id.giakhuyenmai);
            tv_namesale = itemView.findViewById(R.id.tv_namesale);
            constraintLayout =itemView.findViewById(R.id.constraintLayout);

        }
    }
}

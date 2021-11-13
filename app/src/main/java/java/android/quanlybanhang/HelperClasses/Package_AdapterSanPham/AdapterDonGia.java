package java.android.quanlybanhang.HelperClasses.Package_AdapterSanPham;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.android.quanlybanhang.Model.ChucNangThanhToan.DonGia;
import java.android.quanlybanhang.R;
import java.util.ArrayList;


public class AdapterDonGia extends RecyclerView.Adapter<AdapterDonGia.AdapterDonGiaHolder> {
    ArrayList<DonGia> donGias;
    Context context1;
    private Dialog dialog;
    private Window window;
    private EditText textGiaSanPham;
    private Spinner spinnerTenDonVIiTinh;
    private Button btnDialogHuyDVT,btnDialogThemDVT;
    private ArrayAdapter<String> adapter;
    private int gravity;

    public AdapterDonGia(Context context1,ArrayList<DonGia> donGias){
        this.donGias = donGias;
        this.context1 = context1;
    }
    public AdapterDonGia(Context context1, ArrayList<DonGia> donGias,Dialog dialog,Window window,Spinner spinnerTenDonVIiTinh,ArrayAdapter<String> adapter,int gravity){
        this.donGias = donGias;
        this.context1 = context1;
        this.dialog = dialog;
        this.window = window;
        this.spinnerTenDonVIiTinh = spinnerTenDonVIiTinh;
        this.adapter = adapter;
        this.gravity = gravity;

    }

    @NonNull
    @Override
    public AdapterDonGiaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_itemlistdongia,parent,false);
        AdapterDonGiaHolder staticRvViewHolder = new AdapterDonGiaHolder(view);
        return staticRvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDonGiaHolder holder, int position) {
        DonGia donGia = donGias.get(position);
        holder.textView.setText(donGia.getTenDonGia());
        holder.textViewGia.setText(donGia.getGiaBan()+"");

        holder.Xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donGias.remove(donGia);
                notifyDataSetChanged();
            }
        });

        holder.Sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textGiaSanPham = dialog.findViewById(R.id.tedtGiaDonVi);
                spinnerTenDonVIiTinh = dialog.findViewById(R.id.spnTenDonViTinh);
                btnDialogHuyDVT = dialog.findViewById(R.id.btnhuyDiaLogDVT);
                btnDialogThemDVT = dialog.findViewById(R.id.btnthemDiaLogDVT);
                textGiaSanPham.setText(donGia.getGiaBan()+"");
                if (window == null) {
                    return;
                }
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams windownAttributes = window.getAttributes();
                window.setAttributes(windownAttributes);
                if(Gravity.BOTTOM == gravity){
                    dialog.setCancelable(true);
                }
                else {
                    dialog.setCancelable(false);
                }
                if(donGia.getTenDonGia() != null){
                    int com = adapter.getPosition(donGia.getTenDonGia());
                    spinnerTenDonVIiTinh.setSelection(com);
                }
                btnDialogHuyDVT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnDialogThemDVT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.textView.setText(spinnerTenDonVIiTinh.getSelectedItem().toString());
                        holder.textViewGia.setText(textGiaSanPham.getText().toString());
                        textGiaSanPham.setText("");
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return donGias.size();
    }

    public class AdapterDonGiaHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textViewGia;
        ImageView Xoa, Sua;

        public AdapterDonGiaHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.lblName);
            textViewGia = itemView.findViewById(R.id.lbGia);
            Xoa = itemView.findViewById(R.id.XoaDonVi);
            Sua = itemView.findViewById(R.id.UpdateDonVi);

            Xoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    donGias.remove(position);
                }
            });
        }
    }
}

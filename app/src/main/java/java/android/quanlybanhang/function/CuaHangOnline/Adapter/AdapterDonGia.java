package java.android.quanlybanhang.function.CuaHangOnline.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
    private Button btnDialogHuyDVT,btnDialogThemDVT;
    private AutoCompleteTextView spinnerTenDonVIiTinh;
    private ArrayAdapter<String> adapter;
    private int gravity;
    private String name;
    private ArrayList<String> listDonViTinh;

    public AdapterDonGia(Context context1,ArrayList<DonGia> donGias){
        this.donGias = donGias;
        this.context1 = context1;
    }
    public AdapterDonGia(Context context1, ArrayList<DonGia> donGias,Dialog dialog,Window window,ArrayAdapter<String> adapter,int gravity, AutoCompleteTextView spinnerTenDonVIiTinh, ArrayList<String> listDonViTinh){
        this.donGias = donGias;
        this.context1 = context1;
        this.dialog = dialog;
        this.window = window;
        this.adapter = adapter;
        this.gravity = gravity;
        this.spinnerTenDonVIiTinh = spinnerTenDonVIiTinh;
        this.listDonViTinh = listDonViTinh;
    }

    public void setData(ArrayList<DonGia> donGias) {
        this.donGias = donGias;
        notifyDataSetChanged();
    }

    public void setData2(ArrayList<String> listDonViTinh) {
        this.listDonViTinh = listDonViTinh;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterDonGiaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listdongia,parent,false);
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
                    spinnerTenDonVIiTinh.setText(donGia.getTenDonGia());
                    adapter = new ArrayAdapter<String>(context1, R.layout.support_simple_spinner_dropdown_item, listDonViTinh);
                    adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);
                    spinnerTenDonVIiTinh.setAdapter(adapter);
                    notifyDataSetChanged();

                }

                btnDialogHuyDVT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        spinnerTenDonVIiTinh.setText("");
                        textGiaSanPham.setText("");
                        dialog.dismiss();
                    }
                });

                spinnerTenDonVIiTinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        name = parent.getItemAtPosition(position).toString();
                    }
                });

                btnDialogThemDVT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.textView.setText(name);
                        holder.textViewGia.setText(textGiaSanPham.getText().toString());
                        donGias.get(position).setTenDonGia(name);
                        donGias.get(position).setGiaBan(Double.parseDouble(textGiaSanPham.getText().toString()));
                        spinnerTenDonVIiTinh.setText("");
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


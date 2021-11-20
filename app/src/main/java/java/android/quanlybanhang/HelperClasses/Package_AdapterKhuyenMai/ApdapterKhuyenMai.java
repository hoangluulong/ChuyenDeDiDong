package java.android.quanlybanhang.HelperClasses.Package_AdapterKhuyenMai;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.android.quanlybanhang.Model.KhuyenMai.KhuyenMai;
import java.android.quanlybanhang.R;
import java.util.ArrayList;


public class ApdapterKhuyenMai extends RecyclerView.Adapter<ApdapterKhuyenMai.AdapterKhuyenMaiHolder> {
    ArrayList<KhuyenMai> khuyenMais;
    Context context;




    public ApdapterKhuyenMai(Context context, ArrayList<KhuyenMai> khuyenMais){
        this.khuyenMais =khuyenMais;
        this.context = context;

    }


    @NonNull
    @Override
    public AdapterKhuyenMaiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_khuyenmai,parent,false);
        AdapterKhuyenMaiHolder staticRvViewHolder = new AdapterKhuyenMaiHolder(view);
        return staticRvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterKhuyenMaiHolder holder, int position) {
        KhuyenMai khuyenMai = khuyenMais.get(position);
        holder.textViewTen.setText(khuyenMai.getGiaDeDuocKhuyenMai().toString());
        holder.textViewLoai.setText(khuyenMai.getLoaiKhuyenmai()+"");
    }

    @Override
    public int getItemCount() {
        return khuyenMais.size();
    }

    public class AdapterKhuyenMaiHolder extends RecyclerView.ViewHolder {
        TextView textViewTen;
        TextView textViewLoai;
        ImageView imgXoa;
        ImageView imgSua;
        public AdapterKhuyenMaiHolder(@NonNull View itemView) {
            super(itemView);
            textViewTen = itemView.findViewById(R.id.text_tenKM);
            textViewLoai = itemView.findViewById(R.id.text_loaiKM);
            imgXoa = itemView.findViewById(R.id.btnXoaKM);
            imgSua = itemView.findViewById(R.id.btnSuaKM);

            imgSua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    int position = getLayoutPosition();
//                    Intent intent = new Intent(context, SuaKhachHang.class);
//                    intent.putExtra("Key_arrKH",khachHangs.get(position));
//                    context.startActivity(intent);
                }
            });

            imgXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();

                }
            });
        }


    }
}


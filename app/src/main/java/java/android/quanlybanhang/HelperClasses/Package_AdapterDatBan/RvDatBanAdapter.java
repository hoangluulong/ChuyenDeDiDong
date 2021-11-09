package java.android.quanlybanhang.HelperClasses.Package_AdapterDatBan;

import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.android.quanlybanhang.HelperClasses.Pakage_AdapterBan.StaticBanModel;
import java.android.quanlybanhang.HelperClasses.Pakage_AdapterBan.StaticRvAdapter;
import java.android.quanlybanhang.Model.DatBan.ID_datban;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.DatBan.DanhSachDatBan;
import java.android.quanlybanhang.function.SanPham.ListProduct;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RvDatBanAdapter extends RecyclerView.Adapter<RvDatBanAdapter.DatBanholder> {
  private   ArrayList<ID_datban> items;
    private DatabaseReference mDatabaĐanh;
    private DanhSachDatBan danhSachDatBan;
    public RvDatBanAdapter(ArrayList<ID_datban> items,DanhSachDatBan danhSachDatBan){
        this.items = items;
        this.danhSachDatBan = danhSachDatBan;
    }

    @NonNull
    @Override
    public DatBanholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listdatban,parent,false);

        RvDatBanAdapter.DatBanholder datBanholder = new RvDatBanAdapter.DatBanholder(view);

        return datBanholder;
    }

    @Override
    public void onBindViewHolder(@NonNull DatBanholder holder, int position) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.isclick.getLayoutParams();
        if (items.get(position).getDatBanModels().get(position).isUp()) {
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//            holder.updownIMG.setImageResource(R.drawable.up_24);
            holder.isclick.setLayoutParams(params);
        } else {
            params.height = 0;
//            holder.updownIMG.setImageResource(R.drawable.down_24);
            holder.isclick.setLayoutParams(params);
        }
        ID_datban CrrItem = items.get(position);
        Log.d("CrrItem",position+"");
        holder.tvtenkhachhang.setText(CrrItem.getDatBanModels().get(position).getTenkhachhang());
        holder.tvsodienthoai.setText(CrrItem.getDatBanModels().get(position).getSodienthoai());
        holder.tvsotiendattruoc.setText(CrrItem.getDatBanModels().get(position).getSotiendadattruoc());
        holder.tvngaydat.setText(CrrItem.getDatBanModels().get(position).getNgayhientai());
        holder.tvngaydatban.setText(CrrItem.getDatBanModels().get(position).getNgaydat());
        holder.tvgiodat.setText(CrrItem.getDatBanModels().get(position).getGiodat());
        holder.tvtenban.setText(CrrItem.getDatBanModels().get(position).getTenban());
        holder.tvgiokt.setText(CrrItem.getDatBanModels().get(position).getGioketthuc());
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = holder.getLayoutPosition();
                danhSachDatBan.delete(position);

                notifyDataSetChanged();
            }
        });
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] words=items.get(position).getDatBanModels().get(position).getId_bk().split("_");

                FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("khuvuc").child(words[1]).child("ban").child(words[0]).child("trangthai").setValue("4");
                holder.check.setEnabled(true);
                holder.check.setBackgroundResource(R.color.bac);
            }
        });



    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class DatBanholder extends RecyclerView.ViewHolder {
            TextView tvtenkhachhang,tvsodienthoai,tvsotiendattruoc,tvngaydat,tvtenban,tvngaydatban,tvgiodat,tvgiokt;
//            TextInputLayout tvtenkhachhang
            LinearLayout click,isclick;
            ImageButton cancel,check;
        private LinearLayout.LayoutParams params;
        public DatBanholder(@NonNull View itemView) {
            super(itemView);
            tvtenkhachhang = itemView.findViewById(R.id.tvtenkhachhang);
            tvsodienthoai = itemView.findViewById(R.id.tvsodienthoai);
            tvsotiendattruoc = itemView.findViewById(R.id.tvsotiendattruoc);
            tvngaydat = itemView.findViewById(R.id.tvngaydat);
            tvtenban = itemView.findViewById(R.id.tvtenban);
            tvngaydatban = itemView.findViewById(R.id.tvngaydatban);
            tvgiodat = itemView.findViewById(R.id.tvgiodat);
            tvgiokt = itemView.findViewById(R.id.tvgiokt);
            cancel = itemView.findViewById(R.id.cancel);
            check = itemView.findViewById(R.id.check);
            click= itemView.findViewById(R.id.click);
            isclick= itemView.findViewById(R.id.isclick);
            params= (LinearLayout.LayoutParams) isclick.getLayoutParams();
            click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    if (items.get(position).getDatBanModels().get(position).isUp()) {
                        params.height = 0;
                        items.get(position).getDatBanModels().get(position).setUp(false);
//                        updownIMG.setImageResource(R.drawable.down_24);
                        isclick.setLayoutParams(params);
                        notifyDataSetChanged();
                    } else {
                        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        items.get(position).getDatBanModels().get(position).setUp(true);
//                        updownIMG.setImageResource(R.drawable.up_24);
                        isclick.setLayoutParams(params);
                        notifyDataSetChanged();
                    }
                }
            });

        }
    }


}
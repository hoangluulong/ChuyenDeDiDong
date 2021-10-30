package java.android.quanlybanhang.function.BepBar.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.BepBar.Data.DonHang;
import java.android.quanlybanhang.function.BepBar.Data.Mon;
import java.android.quanlybanhang.function.BepBar.Data.SanPham;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DonOnlineChoChoXacNhanAdapter extends RecyclerView.Adapter<DonOnlineChoChoXacNhanAdapter.ViewHolder> {

    Context context;
    List<DonHang> mList;
    private SanPhamDonHangAdapter sanphamHolder;
    private DatabaseReference mDatabase;


    public DonOnlineChoChoXacNhanAdapter(Context context, List<DonHang> mList) {
        this.context = context;
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View heroView = inflater.inflate(R.layout.item_bep_bar_danh_sanh_ban, parent, false);
        return new DonOnlineChoChoXacNhanAdapter.ViewHolder(heroView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.examName.setText(mList.get(position).getKey());
        holder.examDate.setText(mList.get(position).getTime());

        holder.monBan.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        SanPhamDonHangAdapter sanphamHolder = new SanPhamDonHangAdapter(context, mList.get(position).getSanpham());
        holder.monBan.setAdapter(sanphamHolder);




        sanphamHolder.notifyDataSetChanged();
        if (mList.get(position).getTrangthai() == 3) {
            holder.danglam.setBackgroundResource(R.color.purple_200);
        }
        if (mList.get(position).getTrangthai() == 4) {
            holder.danglam.setBackgroundResource(R.color.purple_200);
            holder.done.setBackgroundResource(R.color.purple_500);
        }


        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Accept", Toast.LENGTH_LONG).show();
                if (mList.get(position).getTrangthai() == 2) {
                    FirebaseDatabase.getInstance().getReference().child("JxZOOK1RzcMM7pL5I6naGZfYSsu2/donhangonline/dondadat/" + ngayHientai(mList.get(position).getDate()) + "/" + mList.get(position).getKey() + "/trangthai").setValue(3);
                } else if (mList.get(position).getTrangthai() == 3) {
                    FirebaseDatabase.getInstance().getReference().child("JxZOOK1RzcMM7pL5I6naGZfYSsu2/donhangonline/dondadat/" + ngayHientai(mList.get(position).getDate()) + "/" + mList.get(position).getKey() + "/trangthai").setValue(4);
                }

                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
//        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView examName, examDate;
        private LinearLayout danglam, done;
        private Button accept;
        private RecyclerView monBan;

        public ViewHolder(View itemView) {
            super(itemView);

            examName = itemView.findViewById(R.id.examName);
            examDate = itemView.findViewById(R.id.examDate);

        }
    }

    private void RecyclerViewSanPham(RecyclerView recyclerView, List<SanPham> sanPhams) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        sanphamHolder = new SanPhamDonHangAdapter(context, sanPhams);
        recyclerView.setAdapter(sanphamHolder);
        sanphamHolder.notifyDataSetChanged();
    }

    private String ngayHientai(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String dt = formatter.format(date);
        return dt;
    }
}
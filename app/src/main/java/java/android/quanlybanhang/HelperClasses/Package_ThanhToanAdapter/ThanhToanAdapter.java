package java.android.quanlybanhang.HelperClasses.Package_ThanhToanAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ramotion.foldingcell.FoldingCell;

import java.android.quanlybanhang.Model.ChucNangThanhToan.ProuductPushFB1;
import java.android.quanlybanhang.R;
import java.util.ArrayList;

public class ThanhToanAdapter extends RecyclerView.Adapter<ThanhToanAdapter.CardDaOrderHoler> {
    private  ArrayList<ProuductPushFB1> items;
   public ThanhToanAdapter(ArrayList<ProuductPushFB1> items){
       this.items = items;
   }
   public void setData(ArrayList<ProuductPushFB1> item){
                items= item;
                notifyDataSetChanged();
   }
    @NonNull
    @Override
    public CardDaOrderHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_fordsell,parent,false);
        ThanhToanAdapter.CardDaOrderHoler card = new ThanhToanAdapter.CardDaOrderHoler(view);
        return card;
    }

    @Override
    public void onBindViewHolder(@NonNull CardDaOrderHoler holder, int position) {
        ProuductPushFB1 CrItem=items.get(position);
        holder.tensanpham.setText(CrItem.getNameProduct()+"");
        holder.giasanpham.setText(CrItem.getYeuCau()+"");
//        holder.soluongSanpham.setText(CrItem.getSoluong()+"");
        holder.foldingCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.foldingCell.toggle(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("item.size",items.size()+"");

        if (items!=null){
            return items.size() ;
        }
        return 0;
    }

    public class CardDaOrderHoler extends RecyclerView.ViewHolder {
        TextView tensanpham ;
        TextView giasanpham;
//        TextView soluongSanpham;
        FoldingCell foldingCell;
        public CardDaOrderHoler(@NonNull View itemView) {
            super(itemView);
            foldingCell= itemView.findViewById(R.id.folding_cell);
            tensanpham= itemView.findViewById(R.id.tvtensanpham);
            giasanpham = itemView.findViewById(R.id.tvgiasanpham);
//            soluongSanpham = itemView.findViewById(R.id.tvsoluong);

        }
    }
}

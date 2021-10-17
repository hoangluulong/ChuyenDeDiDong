package java.android.quanlybanhang.OrderMon;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.android.quanlybanhang.KhuVuc.StaticModelKhuVuc;
import java.android.quanlybanhang.KhuVuc.StaticRvKhuVucAdapter;
import java.android.quanlybanhang.PushToFire;
import java.android.quanlybanhang.R;
import java.util.ArrayList;

public class CardDaOrderAdapter extends RecyclerView.Adapter<CardDaOrderAdapter.CardDaOrderHoler> {
    private  ArrayList<PushToFire> items;
   public CardDaOrderAdapter( ArrayList<PushToFire> items){
       this.items = items;
   }
    @NonNull
    @Override
    public CardDaOrderHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardfirebase,parent,false);
        CardDaOrderAdapter.CardDaOrderHoler card = new CardDaOrderAdapter.CardDaOrderHoler(view);
        return card;
    }

    @Override
    public void onBindViewHolder(@NonNull CardDaOrderHoler holder, int position) {
        PushToFire CrItem=items.get(position);
        holder.tensanpham.setText(CrItem.getNameProduct()+"");
        holder.giasanpham.setText(CrItem.getYeuCau()+"");
        holder.soluongSanpham.setText(CrItem.getSoluong()+"");
    }

    @Override
    public int getItemCount() {
        Log.d("item.size",items.size()+"");
        return items.size();
    }

    public class CardDaOrderHoler extends RecyclerView.ViewHolder {
        TextView tensanpham ;
        TextView giasanpham;
        TextView soluongSanpham;
        public CardDaOrderHoler(@NonNull View itemView) {
            super(itemView);
            tensanpham= itemView.findViewById(R.id.tvtensanpham);
            giasanpham = itemView.findViewById(R.id.tvgiasanpham);
            soluongSanpham = itemView.findViewById(R.id.tvsoluong);

        }
    }
}

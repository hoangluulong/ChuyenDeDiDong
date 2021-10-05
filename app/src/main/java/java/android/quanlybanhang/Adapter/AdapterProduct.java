package java.android.quanlybanhang.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.android.quanlybanhang.Data.Product;
import java.android.quanlybanhang.R;

import java.android.quanlybanhang.function.SanPham.ListProduct;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ProductViewHolder> {
    private ArrayList<Product> arrayList;
    private ListProduct context;

    public AdapterProduct( ListProduct context,ArrayList<Product> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        ProductViewHolder productViewHolder = new ProductViewHolder(view);
        return productViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = arrayList.get(position);
        holder.textViewName.setText(product.getNameProduct());
        holder.textViewGia.setText(product.getGiaBan()+"");
        Picasso.get().load(product.getImgProduct()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName;
        private TextView textViewGia;
        private CircleImageView imageView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textnameProduct);
            textViewGia = itemView.findViewById(R.id.textgiaProduct);
            imageView = itemView.findViewById(R.id.circle_imgView);


        }
    }
}

package java.android.quanlybanhang.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.android.quanlybanhang.Data.DonGia;
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
        holder.textViewStatus.setText(product.getStatus());
        holder.textViewName.setText(product.getNameProduct());
        Picasso.get().load(product.getImgProduct()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName;
        private TextView textViewStatus;
        private CircleImageView imageView;
        private ImageView imgXoa;
        private ImageView imgSua;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewStatus = itemView.findViewById(R.id.textnameStatus);
            textViewName = itemView.findViewById(R.id.textnameProduct);
            imageView = itemView.findViewById(R.id.circle_imgView);
            imgXoa = itemView.findViewById(R.id.btnXoaSP);
            imgSua = itemView.findViewById(R.id.btnSuaSP);
            imgXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    context.delete(position);
                }
            });

            imgSua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    context.update(position);
                }
            });
        }
    }
}

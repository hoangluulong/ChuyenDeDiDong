package java.android.quanlybanhang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.android.quanlybanhang.Data.Category;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.SanPham.ListCategory;
import java.util.ArrayList;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.CategoryAdater> {

    private ArrayList<Category> arrayList;
    private ListCategory context;

    public AdapterCategory(ListCategory context,ArrayList<Category> arrayList){
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryAdater onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        CategoryAdater categoryAdater = new CategoryAdater(view);
        return categoryAdater;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdater holder, int position) {
        Category category = arrayList.get(position);
        holder.nameCategory.setText(category.getNameCategory());


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class CategoryAdater extends RecyclerView.ViewHolder  {
        private TextView nameCategory;



        public CategoryAdater(View itemView) {
            super(itemView);

            nameCategory = (TextView) itemView.findViewById(R.id.text_name);
            

        }


    }
}

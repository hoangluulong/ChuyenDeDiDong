package java.android.quanlybanhang.function.CuaHangOnline.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.DonHangOnline.adapter.ChoXacNhanAdapter;
import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder>{

    private Context context;
    private ArrayList<String> listImage;

    public ImageAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.listImage = list;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageHolder(LayoutInflater.from(context).inflate(R.layout.item_imageview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ImageHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private CardView btnDelete;
        public ImageHolder(@NonNull View ItemView) {
            super(ItemView);

            image = ItemView.findViewById(R.id.image);
            btnDelete = ItemView.findViewById(R.id.btnDelete);
        }
    }
}

package java.android.quanlybanhang.HelperClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import java.android.quanlybanhang.Model.PieTongQuan;
import java.android.quanlybanhang.Model.SanPhamTop;
import java.android.quanlybanhang.R;
import java.util.ArrayList;
import java.util.List;

public class SildePieAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<PieTongQuan> pieSanPham;
    ArrayList<PieTongQuan> pieNhomSanPham;

    public SildePieAdapter(Context context, ArrayList<PieTongQuan> pieSanPham, ArrayList<PieTongQuan> pieNhomSanPham){
        this.context = context;
        this.pieSanPham = pieSanPham;
        this.pieNhomSanPham = pieNhomSanPham;
    }



    @Override
    public int getCount() {
        return 2;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_pie, container, false);



        RecyclerView recyclerView = view.findViewById(R.id.recycleTopSanPham);
        TextView textView = view.findViewById(R.id.lblTitle);
        if (position == 0){

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(context, 1));

            List<SanPhamTop> sanPhamTops = new ArrayList<>();
            sanPhamTops.add(new SanPhamTop("Evan", 10));
            sanPhamTops.add(new SanPhamTop("Long", 20));
            sanPhamTops.add(new SanPhamTop("Phia", 30));
            sanPhamTops.add(new SanPhamTop("namfksnd", 40));
            sanPhamTops.add(new SanPhamTop("Evan", 10));
            sanPhamTops.add(new SanPhamTop("Long", 20));
            sanPhamTops.add(new SanPhamTop("Phia", 30));

            CustomTopSanPhamAdapter customTopSanPhamAdapter = new CustomTopSanPhamAdapter(context,sanPhamTops);
            recyclerView.setAdapter(customTopSanPhamAdapter);
        }else{
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
            List<SanPhamTop> sanPhamTops = new ArrayList<>();

            sanPhamTops.add(new SanPhamTop("Evan", 10));
            sanPhamTops.add(new SanPhamTop("Long", 20));
            sanPhamTops.add(new SanPhamTop("Phia", 30));
            sanPhamTops.add(new SanPhamTop("namfksnd", 40));
            sanPhamTops.add(new SanPhamTop("Evan", 10));
            sanPhamTops.add(new SanPhamTop("Long", 20));
            sanPhamTops.add(new SanPhamTop("Phia", 30));

            CustomTopSanPhamAdapter customTopSanPhamAdapter = new CustomTopSanPhamAdapter(context,sanPhamTops);
            recyclerView.setAdapter(customTopSanPhamAdapter);
        }
        container.addView(view);

        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(LinearLayout)object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }

    private void displayItem(){

    }

}


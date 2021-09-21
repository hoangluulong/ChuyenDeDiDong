package java.android.quanlybanhang.function.BaoCao;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.android.quanlybanhang.HelperClasses.QuanLyKhoAdapter;
import java.android.quanlybanhang.Model.BaoCaoKho;
import java.android.quanlybanhang.R;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class BaoCaoKhoActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private QuanLyKhoAdapter quanLyKhoAdapter;
    private List<BaoCaoKho> baoCaoKhos;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_cao_kho);
        displayItem();
        back = findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void displayItem() {
        recyclerView = findViewById(R.id.recycler_bao_cao_kho);

        baoCaoKhos = new ArrayList<>();

        recyclerView.hasFixedSize();
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        quanLyKhoAdapter = new QuanLyKhoAdapter(this, baoCaoKhos);
        recyclerView.setAdapter(quanLyKhoAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        getData();
    }

    private void getData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                baoCaoKhos.add(new BaoCaoKho("Hoàng Hữu Long 0", "20-08-2021", "20:39:00"));
                baoCaoKhos.add(new BaoCaoKho("Hoàng Hữu Long 1", "23-08-2021", "20:40:00"));
                baoCaoKhos.add(new BaoCaoKho("Hoàng Hữu Long 2", "20-08-2021", "20:59:00"));
                baoCaoKhos.add(new BaoCaoKho("Hoàng Hữu Long 3", "21-08-2021", "20:37:01"));
                baoCaoKhos.add(new BaoCaoKho("Hoàng Hữu Long 4", "20-08-2021", "20:36:04"));
                baoCaoKhos.add(new BaoCaoKho("Hoàng Hữu Long 5", "25-08-2021", "20:36:03"));

                quanLyKhoAdapter.isShimmer = false;
                sapXepList();
                quanLyKhoAdapter.notifyDataSetChanged();
            }
        }, 5000);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            AlertDialog.Builder builder = new AlertDialog.Builder(BaoCaoKhoActivity.this);
            builder.setTitle("Dữ liệu xóa sẽ không thể khôi phục");
            builder.setMessage("Bạn có muốn xóa không?");
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int position = viewHolder.getAdapterPosition();
                    baoCaoKhos.remove(position);
                    quanLyKhoAdapter.notifyItemRemoved(position);
                }
            });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    quanLyKhoAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                }
            });
            builder.show();
        }
    };

    private void sapXepList() {
        Collections.sort(baoCaoKhos, new Comparator<BaoCaoKho>() {
            @Override
            public int compare(BaoCaoKho o1, BaoCaoKho o2) {
                Date dt1 = chuyenStringThanhDate(o1.getNgay(), o1.getGio());
                Date dt2 = chuyenStringThanhDate(o2.getNgay(), o2.getGio());
                if (dt1.after(dt2)) {
                    return -1;
                } else if (dt1.equals(dt2)) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
    }

    private Date chuyenStringThanhDate(String ngay, String gio) {
        String date_s = ngay + " " + gio;
        DateFormat dt = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        Date date;
        try {
            date = dt.parse(date_s);
        } catch (Exception e) {
            return null;
        }
        return date;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
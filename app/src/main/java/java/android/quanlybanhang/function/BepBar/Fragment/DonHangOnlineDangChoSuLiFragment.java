package java.android.quanlybanhang.function.BepBar.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.android.quanlybanhang.R;
import java.android.quanlybanhang.function.BepBar.Adapter.DonOnlineChoChoXacNhanAdapter;
import java.android.quanlybanhang.function.BepBar.Adapter.SanPhamDonHangAdapter;
import java.android.quanlybanhang.function.BepBar.BepActivity;
import java.android.quanlybanhang.function.BepBar.Data.DonHang;
import java.android.quanlybanhang.function.BepBar.Data.Mon;
import java.android.quanlybanhang.function.BepBar.Data.SanPham;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DonHangOnlineDangChoSuLiFragment extends Fragment {
    private RecyclerView recyclerViewTable;
    private DatabaseReference mDatabase;
    private DonOnlineChoChoXacNhanAdapter monViewHolder;
    View v;
    private ArrayList<DonHang> donHangs;
    private ProgressBar progressBar;
    private TextView lblThongBao;
    private ImageView imageView;

    public DonHangOnlineDangChoSuLiFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_bep_danh_sanh_don_hang, container, false);

        progressBar = v.findViewById(R.id.progressBar);
        lblThongBao = v.findViewById(R.id.lblThongBao);
        imageView = v.findViewById(R.id.image);

        progressBar.setVisibility(View.VISIBLE);

        getDataFirebase();
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void getDataFirebase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("JxZOOK1RzcMM7pL5I6naGZfYSsu2/donhangonline/dondadat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                donHangs = new ArrayList<>();
                int i = 0;
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot snap : postSnapshot.getChildren()) {
                        DonHang donHang = snap.getValue(DonHang.class);
                        if (donHang.getTrangthai() == 2) {
                            donHangs.add(donHang);
                            Date date = formatDate(donHangs.get(i).getTime());
                            donHangs.get(i).setDate(date);
                            i++;
                        }
                    }
                }

                progressBar.setVisibility(View.INVISIBLE);
                if (donHangs.size() > 0) {
                    lblThongBao.setText("");
                    imageView.setImageResource(0);
                }else {
                    lblThongBao.setText("Chưa có đơn hàng nào");
                    imageView.setImageResource(R.drawable.empty_list);
                }

                IDLayout();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void IDLayout() {
        recyclerViewTable = v.findViewById(R.id.recycler);
        recyclerViewTable.setHasFixedSize(true);
        recyclerViewTable.setLayoutManager(new LinearLayoutManager(getActivity()));
        monViewHolder = new DonOnlineChoChoXacNhanAdapter(v.getContext(), donHangs);
        recyclerViewTable.setAdapter(monViewHolder);
        monViewHolder.notifyDataSetChanged();
    }

    private Date formatDate(String strDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss.sss yyyy-MM-dd");

        try {
            Date date = simpleDateFormat.parse(strDate);
            return date;
        } catch (Exception e) {
            Date date = new Date();
            return date;
        }
    }
}

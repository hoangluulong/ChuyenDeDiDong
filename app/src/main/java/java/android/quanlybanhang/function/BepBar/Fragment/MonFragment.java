package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MonFragment extends Fragment {
    private RecyclerView recyclerViewTable;
    private DatabaseReference mDatabase;
    private BepActivity bepActivity;
    private List<Mon> list;
    private List<String> stringList;
    private MonViewHolder monViewHolder;
    private ArrayList<SanPham> sanPham;
    View v;
    private ArrayList<DonHang> donHangs;
    public MonFragment(BepActivity bepActivity) {
        this.bepActivity=bepActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.frag_mon, container, false);
        getDataFirebase();
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stringList=new ArrayList<>();
    }

    private void getDataFirebase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("JxZOOK1RzcMM7pL5I6naGZfYSsu2/donhangonline/dondadat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                donHangs = new ArrayList<>();
                sanPham = new ArrayList<>();
                int i = 0;
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot snap : postSnapshot.getChildren()) {
                        DonHang donHang = snap.getValue(DonHang.class);
                        if (donHang.getTrangthai() == 2 || donHang.getTrangthai() == 3 || donHang.getTrangthai() == 4) {
                            donHangs.add(donHang);
                            Date date = formatDate(donHangs.get(i).getTime());
                            donHangs.get(i).setDate(date);
                            i++;
                        }
                    }
                }
                IDLayout();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void IDLayout() {
        recyclerViewTable= v.findViewById(R.id.recycler);
        recyclerViewTable.setHasFixedSize(true);
        recyclerViewTable.setLayoutManager(new LinearLayoutManager(getActivity()));
        monViewHolder = new MonViewHolder(v.getContext(), donHangs);
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

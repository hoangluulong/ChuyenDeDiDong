package java.android.quanlybanhang.function.BepBar.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.android.quanlybanhang.function.BepBar.Adapter.DonHangOfflineAdapter;
import java.android.quanlybanhang.function.BepBar.Data.SanPhamOder;
import java.util.ArrayList;

public class DonHangOfflineFragment extends Fragment {
    private RecyclerView recyclerViewTable;
    private DatabaseReference mDatabase;
    private ArrayList<SanPhamOder> tableList;
    private DonHangOfflineAdapter donHangOfflineAdapter;

    View v;
    public DonHangOfflineFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        onDataChange();
        tableList=new ArrayList<>();
        v= inflater.inflate(R.layout.fragment_danh_sanh_don_hang, container, false);
        recyclerViewTable= v.findViewById(R.id.recycler);
        donHangOfflineAdapter =new DonHangOfflineAdapter(getActivity(),getContext());
        return v;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private void onDataChange()
    {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("sanphamorder").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i=0;
                tableList=new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    SanPhamOder sanPhamOder = postSnapshot.getValue(SanPhamOder.class);
                    String key=postSnapshot.getKey();
                    if(sanPhamOder.getTrangThai()!=3){
                        tableList.add(sanPhamOder);
                        tableList.get(i).setNameTable(key);
                        donHangOfflineAdapter.setData(tableList);
                        i++;
                    }
//                           for(int j=0;j<tableList.size();j++){
//                               if(tableList.get(j).getTrangThai()==3){
//                                   tableList.remove(j);
//                               }
//                           }
                }
                donHangOfflineAdapter.setData(tableList);
                recyclerViewTable.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerViewTable.setAdapter(donHangOfflineAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}


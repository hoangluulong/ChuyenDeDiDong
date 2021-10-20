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

import java.util.ArrayList;
import java.util.List;

public class TableFragment extends Fragment {
    private RecyclerView recyclerViewTable;
    private DatabaseReference mDatabase;
    private ArrayList<Table> tableList;
    private TableViewHolder tableViewHolder;


    View v;
    public TableFragment() {
        // Required empty public constructor
        Log.d("III","ACascsa");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        onDataChange();
        tableList=new ArrayList<>();
        v= inflater.inflate(R.layout.frag_ban, container, false);
        recyclerViewTable= v.findViewById(R.id.recycler);
        tableViewHolder=new TableViewHolder(getActivity(),getContext());
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
                        Table table = postSnapshot.getValue(Table.class);
                        String key=postSnapshot.getKey();
                        if(table.getTrangThai()!=3){
                            tableList.add(table);
                            tableList.get(i).setNameTable(key);
                            tableViewHolder.setData(tableList);
                            i++;
                        }
//                           for(int j=0;j<tableList.size();j++){
//                               if(tableList.get(j).getTrangThai()==3){
//                                   tableList.remove(j);
//                               }
//                           }

                    }
                tableViewHolder.setData(tableList);
                recyclerViewTable.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerViewTable.setAdapter(tableViewHolder);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

}

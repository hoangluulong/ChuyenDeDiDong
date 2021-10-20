package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Choreographer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TableViewHolder extends RecyclerView.Adapter<TableViewHolder.ViewHolderBan> {
    public TableViewHolder(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
        this.coLor=0;
    }
    public void setData(List<Table> list){
        this.tableList=list;
        notifyDataSetChanged();
    }
    int coLor;
    private DatabaseReference mDatabase;
    Activity activity;
    Context context;
    List<Table> tableList;


    @Override
    public ViewHolderBan onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View heroView = inflater.inflate(R.layout.design_ban, parent, false);
        return new ViewHolderBan(heroView);
    }

    @Override
    public void onBindViewHolder(TableViewHolder.ViewHolderBan holder, int position) {
        Table table = tableList.get(position);
        holder.examName.setText(table.getNameTable());
        holder.examDate.setText(table.getDate()+"");
        MonBanViewHolder monBanViewHolder=new MonBanViewHolder();
        monBanViewHolder.setmList(table.getSanpham());
        holder.monBan.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));
        holder.monBan.setAdapter(monBanViewHolder);
        if(table.getTrangThai()==1){
            holder.danglam.setBackgroundResource(R.color.trangthai);
            holder.aceept.setText("Da Lam");
        }else if(table.getTrangThai()==2){
            holder.danglam.setBackgroundResource(R.color.trangthai);
            holder.done.setBackgroundResource(R.color.trangthai);
            holder.aceept.setText("Xoa");
        }
        holder.aceept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase = FirebaseDatabase.getInstance().getReference().child("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("sanphamorder");
                if(table.getTrangThai()==0){
                    mDatabase.child(table.getNameTable()).child("trangThai").setValue(1);
                    holder.danglam.setBackgroundResource(R.color.trangthai);
                    holder.aceept.setText("Da Lam");
                    table.setTrangThai(1);
                }else if(table.getTrangThai()==1){
                    mDatabase.child(table.getNameTable()).child("trangThai").setValue(2);
                    holder.done.setBackgroundResource(R.color.trangthai);
                    holder.aceept.setText("Xoa");
                    table.setTrangThai(2);
                }else if(table.getTrangThai()==2){
                    mDatabase.child(table.getNameTable()).child("trangThai").setValue(3);
                    table.setTrangThai(3);
                    holder.aceept.setEnabled(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tableList.size();
    }


    public class ViewHolderBan extends RecyclerView.ViewHolder {
        private TextView examName;
        private TextView examDate;
        private RecyclerView monBan;
        private LinearLayout danglam;
        private LinearLayout done;
        private Button aceept;

        public ViewHolderBan(View itemView) {
            super(itemView);
            examName
                    = itemView
                    .findViewById(R.id.examName);
            examDate
                    = itemView
                    .findViewById(R.id.examDate);
            monBan
                    =itemView
                    .findViewById(R.id.monBan);
            danglam
                    =itemView
                    .findViewById(R.id.danglam);
            done
                    =itemView
                    .findViewById(R.id.done);
            aceept
                    =itemView
                    .findViewById(R.id.accept);

        }

    }

}

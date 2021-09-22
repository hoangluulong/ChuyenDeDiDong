package java.android.quanlybanhang.KhuVuc;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.android.quanlybanhang.Ban.StaticBanModel;
import java.android.quanlybanhang.Interface_KhuVuc_ban;
import java.android.quanlybanhang.R;
import java.util.ArrayList;

public class StaticRvKhuVucAdapter  extends RecyclerView.Adapter<StaticRvKhuVucAdapter.StaticRvViewHolder>{
//    Context context;
    ArrayList<StaticModelKhuVuc> items;
    int item_a =-1;
    Interface_KhuVuc_ban interfacekhuVucban;

    Activity activity;
    boolean check = true;
    boolean select= true;
    private  String ID;
    private DatabaseReference kvDatabase;//khai bao database
    public StaticRvKhuVucAdapter(ArrayList<StaticModelKhuVuc> items, Activity activity, Interface_KhuVuc_ban interfacekhuVucban) {
        this.items = items;
        this.activity= activity;
        this.interfacekhuVucban = interfacekhuVucban;
//        this.context = context;

    }
//

    @NonNull
    @Override
    public StaticRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_khuvuc,parent,false);
        StaticRvViewHolder staticRvViewHolder = new StaticRvViewHolder(view);
        return staticRvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StaticRvViewHolder holder,int position) {
        StaticModelKhuVuc CrItem=items.get(position);
        holder.textView.setText(CrItem.getTenkhuvuc());
        holder.textview1.setText(CrItem.getTrangthai());
        if(check){

            interfacekhuVucban.GetBack(0,items.get(0).getStaticBanModels());
            check= false;
        }


        holder.linearLayouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item_a = position;
                String a="1";
                ArrayList<StaticBanModel> item = new ArrayList<>();
                notifyDataSetChanged();
//

//                    kvDatabase = FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("khuvuc");

//                    kvDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
////                            final ArrayList<StaticModelKhuVuc> item = new ArrayList<>();
//                            ArrayList<StaticBanModel> mm= new ArrayList<>();
//                            for (DataSnapshot postSnapshot: snapshot.getChildren()) {
//                                ArrayList<String> ID_KV = new ArrayList<String>();
//                                ID_KV.add(postSnapshot.getKey());
//                                for(int i=0; i<ID_KV.size();i++){
//                                    Log.d("ID_KV",ID_KV.get(i));
//                                }
//                                String id  = postSnapshot.getKey();
//                                Log.d("IDD",postSnapshot.getKey());
//                                DataSnapshot sss = postSnapshot.child("ban");
//                                for (DataSnapshot aaa: sss.getChildren()){
//                                    String ID_ban= aaa.child("id").getValue()+"";
//                                    Log.d("123",aaa.getKey() +"");
//                                    for(int i= 0; i<items.size();i++)
//                                    {
//                                    if(position==0){
//
////                                        Log.d("234",position+"");
//
//                                                String tenban= aaa.child("tenban").getValue()+"";
//                                                String trangthai1=postSnapshot.child("trangthai").getValue()+"";
//
//                                                mm.add(new StaticBanModel(tenban,trangthai1));
//                                            }
////                                    Log.d("kuku", aaa.child("id").getValue()+"");
////                                    Log.d("kaka",aaa.getKey()+"");
//
//                                        interfacekhuVucban.GetBack(position,mm);
//                                    }
//                                    if(position==1){
//                                        ArrayList<StaticBanModel> xxx = new ArrayList<StaticBanModel>();
//                                        xxx.add(new StaticBanModel("Ban3","1"));
//                                        xxx.add(new StaticBanModel("Bana","2"));
//                                        xxx.add(new StaticBanModel("Ban5","3"));
//                                        xxx.add(new StaticBanModel("Ban6","3"));
//                                        interfacekhuVucban.GetBack(position,xxx);
//                                    }
//
//                                }
//
//
//                            }
////                            recyclerView = findViewById(R.id.rv_1);
////                            staticRvKhuVucAdapter = new StaticRvKhuVucAdapter(item, OrderMenu.this,OrderMenu.this);
////                            recyclerView.setLayoutManager(new LinearLayoutManager(OrderMenu.this,LinearLayoutManager.HORIZONTAL,false));
////                            recyclerView.setAdapter(staticRvKhuVucAdapter);
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                    ArrayList<StaticBanModel> items = new ArrayList<StaticBanModel>();
//                    items.add(new StaticBanModel("Ban3","1", "Thach","11h"));
//                    items.add(new StaticBanModel("Bana","2", "Ku","11h"));
//                    items.add(new StaticBanModel("Ban5","3", "kuku","10h"));
//                    items.add(new StaticBanModel("Ban6","3", "kuku","10h"));
//                    items.add(new StaticBanModel("Ban7","3", "kuku","10h"));
//                    items.add(new StaticBanModel("Ban8","3", "kuku","10h"));
//                    interfacekhuVucban.GetBack(position,items);


//                if(position==0){
//                    ArrayList<StaticBanModel> items = new ArrayList<StaticBanModel>();
//                    items.add(new StaticBanModel("Ban3","1", "Thach","11h"));
//                    items.add(new StaticBanModel("Ban4","2", "Ku","11h"));
//                    items.add(new StaticBanModel("Ban5","3", "kuku","10h"));
//                    items.add(new StaticBanModel("Ban6","3", "kuku","10h"));
//                    items.add(new StaticBanModel("Ban7","3", "kuku","10h"));
//                    items.add(new StaticBanModel("Ban8","3", "kuku","10h"));
//                    interfacekhuVucban.GetBack(position,items);
//                }
//                else if (position==1){
//                    ArrayList<StaticBanModel> items = new ArrayList<StaticBanModel>();
//                    items.add(new StaticBanModel("table1","1", "Thach","11h"));
//                    items.add(new StaticBanModel("table2","2", "Ku","11h"));
//                    items.add(new StaticBanModel("table3","3", "kuku","10h"));
//                    items.add(new StaticBanModel("table4","3", "kuku","10h"));
//                    items.add(new StaticBanModel("table5","3", "kuku","10h"));
//                    items.add(new StaticBanModel("table6","3", "kuku","10h"));
//                    interfacekhuVucban.GetBack(position,items);
//                }
//                else if (position==2){
//                    ArrayList<StaticBanModel> items = new ArrayList<StaticBanModel>();
//                    items.add(new StaticBanModel("table7","1", "Thach","11h"));
//                    items.add(new StaticBanModel("table8","2", "Ku","11h"));
//                    items.add(new StaticBanModel("table9","3", "kuku","10h"));
//                    items.add(new StaticBanModel("table10","3", "kuku","10h"));
//                    items.add(new StaticBanModel("table11","3", "kuku","10h"));
//                    items.add(new StaticBanModel("table12","3", "kuku","10h"));
//                    interfacekhuVucban.GetBack(position,items);
//                }
//                else if (position==3){
//                    ArrayList<StaticBanModel> items = new ArrayList<StaticBanModel>();
//                    items.add(new StaticBanModel("table13","1", "Thach","11h"));
//                    items.add(new StaticBanModel("table14","2", "Ku","11h"));
//                    items.add(new StaticBanModel("table15","3", "kuku","10h"));
//                    items.add(new StaticBanModel("table16","3", "kuku","10h"));
//                    items.add(new StaticBanModel("table17","3", "kuku","10h"));
//                    items.add(new StaticBanModel("table18","3", "kuku","10h"));
//                    interfacekhuVucban.GetBack(position,items);
//                }
//                else if (position==4){
//                    ArrayList<StaticBanModel> items = new ArrayList<StaticBanModel>();
//                    items.add(new StaticBanModel("tablea","1", "Thach","11h"));
//                    items.add(new StaticBanModel("tableb","2", "Ku","11h"));
//                    items.add(new StaticBanModel("tablec","3", "kuku","10h"));
//                    items.add(new StaticBanModel("tabled","3", "kuku","10h"));
//                    items.add(new StaticBanModel("tablee","3", "kuku","10h"));
//                    items.add(new StaticBanModel("tablef","3", "kuku","10h"));
//                    interfacekhuVucban.GetBack(position,items);
//                }

        Log.d("khungaa",item.size()+"");
//                holder.constraintLayouts.setBackgroundResource(R.drawable.rv_khuvuc_hong_bg);
//                Log.d("khungaa",items.get(position).getStaticBanModels().get(0).getTrangthai());
    for(int i=0;i<items.size();i++){
        if(position==i){

            interfacekhuVucban.GetBack(position,items.get(position).getStaticBanModels());

                        }
                  }
            }
        });

//        Log.d("khungaa",items.get(position).getStaticBanModels().get(1).getTrangthai());
//        if(items.get(position).getStaticBanModels().get(0).getTrangthai().equals("aaa")){
//            holder.constraintLayouts.setBackgroundResource(R.drawable.rv_khuvuc_hong_bg);
//        }
        if (select) {
            if(position==0)
                holder.linearLayouts.setBackgroundResource(R.drawable.rv_khuvuc_bg);

            select = false;
        }

        else if (items.get(position).getTrangthai().equals("4")){
            holder.linearLayouts.setBackgroundResource(R.drawable.rv_khuvuc_hong_bg);

        }


        else {
//            if(items.get(position).getStaticBanModels().get(position).getTrangthai().equals("2")){
////            holder.constraintLayouts.setBackgroundResource(R.drawable.rv_khuvuc_hong_bg);
//                holder.linearLayouts.setBackgroundResource(R.drawable.rv_khuvuc_hong_bg);
//            }
            if (item_a == position) {
                holder.linearLayouts.setBackgroundResource(R.drawable.rv_khuvuc_bg);
            } else {
                holder.linearLayouts.setBackgroundResource(R.drawable.rv_khuvuc_selected_bg);
            }
        }


    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class StaticRvViewHolder extends RecyclerView.ViewHolder{
        TextView textView ;
        TextView textview1;
        LinearLayout linearLayouts;
        ConstraintLayout constraintLayouts ;
        public StaticRvViewHolder(@NonNull View itemView) {
            super(itemView);
            textView =itemView.findViewById(R.id.tvdanhmucsanpham);
            textview1 = itemView.findViewById(R.id.tvtrangthai);

            linearLayouts = itemView.findViewById(R.id.linerlayouts);

        }
    }

}

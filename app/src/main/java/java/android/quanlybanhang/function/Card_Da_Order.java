package java.android.quanlybanhang.function;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.android.quanlybanhang.Ban.StaticBanModel;
import java.android.quanlybanhang.ChiTietSanPham.StaticCardAdapter;
import java.android.quanlybanhang.OrderMon.CardDaOrderAdapter;
import java.android.quanlybanhang.OrderMon.Mon;
import java.android.quanlybanhang.OrderMon.PushToFire1;
import java.android.quanlybanhang.PushToFire;
import java.android.quanlybanhang.R;
import java.util.ArrayList;

public class Card_Da_Order extends AppCompatActivity {
private ArrayList<String> list ;
private String id_ban,id_khuvuc;
private DatabaseReference mDatabase;
private CardDaOrderAdapter cardDaOrderAdapter;
private RecyclerView recyclerView;
private int kt=-1;
private  ArrayList<PushToFire> listmon = new ArrayList<>();
    private  ArrayList<PushToFire1> ListDate_yc = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_card_da_order);

        Intent intent1 = getIntent();
        id_ban = intent1.getStringExtra("id_ban");
//        Log.d("getkeyabc",id_ban+"getabc");
        id_khuvuc = intent1.getStringExtra("id_khuvuc");
        list= new ArrayList<>();
        getData();
//        kiemtra();
        Log.d("ListDate_yc",ListDate_yc.size()+"getabc");


    }
    public  void getData(){
        mDatabase = FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("sanphamorder").child(id_ban+"_"+id_khuvuc);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//               if(snapshot.getValue()!=null){
                   ListDate_yc = new ArrayList<>();
                   listmon = new  ArrayList<PushToFire>();
                   Log.d("getkeyabc",snapshot.getKey()+"getabc");
                   Log.d("dateFirebase",snapshot.child("date").getValue()+"");
//                   Long date=Long.parseLong(snapshot.child("date").getValue()+"") ;
                   String flag=snapshot.child("flag").getValue()+"";
                   DataSnapshot sss = snapshot.child("sanpham");
                   for (DataSnapshot postSnapshot: sss.getChildren()) {

                       String nameProduct= postSnapshot.child("nameProduct").getValue()+"";
                       Log.d("dateFirebase",nameProduct+"nameProduct");
                       int soluong=Integer.parseInt(postSnapshot.child("soluong").getValue()+"");
                       String yeuCau=postSnapshot.child("yeuCau").getValue()+"";
                       listmon.add( new PushToFire(nameProduct,soluong,yeuCau));
                       Log.d("listmon",listmon+"listmon");

                   }
                   ListDate_yc.add(new PushToFire1(1,true,listmon));

                   recyclerView = findViewById(R.id.rv_3);
                   Log.d("listmon",listmon.size()+"getabc");
                   cardDaOrderAdapter = new CardDaOrderAdapter(listmon);
//                cardDaOrderAdapter.Setdata(listcard);
                   recyclerView.setLayoutManager(new LinearLayoutManager(Card_Da_Order.this,LinearLayoutManager.VERTICAL,false));
                   recyclerView.setAdapter(cardDaOrderAdapter);
                   cardDaOrderAdapter.notifyDataSetChanged();
                        kt=1;
//               }
//               else {
//                   kt=0;
//               }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(Card_Da_Order.this,OrderMenu.class);
//        startActivity(intent);
//        finish();
//    }

//    public void kiemtra(){
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if(kt==1){
//
//                    Toast.makeText(Card_Da_Order.this,"Card đã có sản phẩm",Toast.LENGTH_LONG).show();
//                }
//                else if(kt==0){
//                    Intent intent = new Intent(Card_Da_Order.this,MonOrder.class);
//                    intent.putExtra("id_khuvuc",id_khuvuc);
//                    intent.putExtra("id_ban",id_ban);
//                    startActivity(intent);
//                    Toast.makeText(Card_Da_Order.this,"Card chua có sản phẩm",Toast.LENGTH_LONG).show();
//                }else {
//                    kiemtra();
//                }
//            }
//        },0);
//
//    }
}
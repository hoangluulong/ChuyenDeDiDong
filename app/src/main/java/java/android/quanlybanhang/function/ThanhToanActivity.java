package java.android.quanlybanhang.function;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.android.quanlybanhang.OrderMon.CardDaOrderAdapter;
import java.android.quanlybanhang.OrderMon.PushToFire1;
import java.android.quanlybanhang.PushToFire;
import java.android.quanlybanhang.R;
import java.util.ArrayList;

public class ThanhToanActivity extends AppCompatActivity {
private ArrayList<String> list ;
private String id_ban,id_khuvuc;
private DatabaseReference mDatabase;
private CardDaOrderAdapter cardDaOrderAdapter;
private RecyclerView recyclerView;
private ImageView imgCart;
private TextView nameactivity;
private Button bnt_thanhThoan;
private int kt=-1;
private  ArrayList<PushToFire> listmon = new ArrayList<>();
    private Toolbar toolbar;//tool bar khai bao id
    private  ArrayList<PushToFire1> ListDate_yc = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_thanhtoan);
        toolbar = findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
//             viet su kien cho toolbar
        ActionBar actionBar = getSupportActionBar();
//Thiết lập tiêu đề nếu muốn
        actionBar.setTitle("Thanh Toán");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        Intent intent1 = getIntent();
        id_ban = intent1.getStringExtra("id_ban");
//        Log.d("getkeyabc",id_ban+"getabc");
        id_khuvuc = intent1.getStringExtra("id_khuvuc");
        imgCart = findViewById(R.id.img_order);
        bnt_thanhThoan = findViewById(R.id.bnt_luu);


//        nameactivity = findViewById(R.id.tvtenactivity);
//        String tenActivity="Card"+id_ban;
//        nameactivity.setText(tenActivity);
        list= new ArrayList<>();
        OrderCart();
        getData();
//        kiemtra();
        Log.d("ListDate_yc",ListDate_yc.size()+"getabc");


    }
    public void OrderCart(){
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThanhToanActivity.this,MonOrder.class);
                intent.putExtra("id_ban",id_ban);
                Log.d("id_khuvuc",id_khuvuc);
                intent.putExtra("id_khuvuc",id_khuvuc);
                startActivity(intent);
            }
        });
    }
    public  void getData(){
        mDatabase = FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("sanphamorder").child(id_ban+"_"+id_khuvuc);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               if(snapshot.getValue()!=null) {
                   ListDate_yc = new ArrayList<>();
                   listmon = new ArrayList<PushToFire>();
                   Log.d("getkeyabc", snapshot.getKey() + "getabc");
                   Log.d("dateFirebase", snapshot.child("date").getValue() + "");
//                   Long date=Long.parseLong(snapshot.child("date").getValue()+"") ;
                   String flag = snapshot.child("flag").getValue() + "";
                   DataSnapshot sss = snapshot.child("sanpham");
                   for (DataSnapshot postSnapshot : sss.getChildren()) {
                       String nameProduct = postSnapshot.child("nameProduct").getValue() + "";
                       Log.d("dateFirebase", nameProduct + "nameProduct");
                       int soluong = Integer.parseInt(postSnapshot.child("soluong").getValue() + "");
                       String yeuCau = postSnapshot.child("yeuCau").getValue() + "";
                       listmon.add(new PushToFire(nameProduct, soluong, yeuCau));
                       Log.d("listmon", listmon + "listmon");

                   }
                   ListDate_yc.add(new PushToFire1(1, true, listmon));

                   recyclerView = findViewById(R.id.rv_3);
                   Log.d("listmon", listmon.size() + "getabc");
                   cardDaOrderAdapter = new CardDaOrderAdapter(listmon);
                   recyclerView.setLayoutManager(new LinearLayoutManager(ThanhToanActivity.this, LinearLayoutManager.VERTICAL, false));
                   recyclerView.setAdapter(cardDaOrderAdapter);
                   cardDaOrderAdapter.notifyDataSetChanged();
               }
               else {
                   bnt_thanhThoan.setEnabled(false);
               }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(ThanhToanActivity.this,OrderMenu.class);
//        startActivity(intent);
////        finish();
//    }

}
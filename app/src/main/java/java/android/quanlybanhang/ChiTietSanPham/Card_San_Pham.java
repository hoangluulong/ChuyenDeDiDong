package java.android.quanlybanhang.ChiTietSanPham;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.android.quanlybanhang.OrderMon.Product;
import java.android.quanlybanhang.OrderMon.PushToFire1;
import java.android.quanlybanhang.PushToFire;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.database.Database_order;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Card_San_Pham extends AppCompatActivity {
    private Toolbar toolbar;//tool bar khai bao id
    private RecyclerView rv_3;//tool bar khai bao id
     StaticCardAdapter staticCartAdapter;
     ArrayList<Product> listcard;
    PushToFire list;
    ArrayList<PushToFire> listSP;
     private  PushToFire1 pushToFire1;
     String id_ban;
     String id_khuvuc;
     String id;
     String a;
     private Product staticMonOrderModel;
     Button bntluu,bntthanhtoan;
    private Database_order database_order;
    private PushToFire pushToFire;
     private boolean flag;
     String yeuCau;
     private TextView tvkhongsanpham,tvtentongsp;
    private final String TEN_BANG="ProductSQL";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_san_pham);
        bntluu = findViewById(R.id.bnt_luu);
        bntthanhtoan= findViewById(R.id.bnt_thanhtoan);
        toolbar = findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
        //viet su kien cho toolbar
        ActionBar actionBar = getSupportActionBar();
        //Thiết lập tiêu đề nếu muốn
        actionBar.setTitle("");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        tvkhongsanpham = findViewById(R.id.tv_khongsanpham);
        tvtentongsp = findViewById(R.id.tvtentongsp);
        //
        Intent intent = getIntent();
        id_ban = intent.getStringExtra("id_ban");
        id_khuvuc = intent.getStringExtra("id_khuvuc");
        id=id_ban+"_"+id_khuvuc;
        Log.d("mama",id+"suboi");
        rv_3 = findViewById(R.id.rv_3);
        listcard = new ArrayList<Product>();
//        list = new ArrayList<PushToFire>();
        listSP = new ArrayList<>();
        boolean flag = true;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

//        int date1=Integer.parseInt(timestamp+"");
        Log.d("datess",timestamp+"");
        long date =Long.parseLong(timestamp.getTime()+"");

//        if()
        getDulieuSql();

        pushData(listSP,date,flag);
//        listcard.add(new Product("aaa",30000.0,"aaa","1"));
        staticCartAdapter = new StaticCardAdapter();
        staticCartAdapter.Setdata(listcard);
        rv_3.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rv_3.setAdapter(staticCartAdapter);
        staticCartAdapter.notifyDataSetChanged();

    }
    private  void getDulieuSql( ){

        database_order= new Database_order(this,"app_database.sqlite",null,2);
//        database_order.GetData("SELECT * FROM databaseorder2 ");
        Log.d("aaaaa","aaaas");
        database_order.QueryData("CREATE TABLE IF NOT EXISTS "+TEN_BANG+"(" +
                "Id VARCHAR(20)," +
                "tensanpham VARCHAR(50), " +
                "soluong INTEGER DEFAULT 0, " +
                "image TEXT, " +
                "gia DOUBLE, " +
                "yeuCau TEXT);");
        Log.d("aaaaa","aaaa");


        ArrayList<Product> arrayList = new ArrayList<>();
        String S="SELECT * FROM "+TEN_BANG+" WHERE Id='"+id+"'";
        Cursor cursor =  database_order.GetData(S,null);
        Log.d("11111",cursor+"1111");

        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                a = cursor.getString(0);
                Log.d("cosql",a);
//                Log.d("cosql",id+"nn");


                    String tensp= cursor.getString(1);
                    int  soluong= cursor.getInt(2);
                    String img= cursor.getString(3);
                    double  gia= cursor.getInt(4);
                    String yeuCau = cursor.getString(5);
                    Log.d("yeuCauSQL1",yeuCau);
                    listcard.add(new Product(a,tensp,soluong,img,gia));
                    listSP.add(new PushToFire(tensp,soluong,yeuCau));
                tvtentongsp.setVisibility(View.VISIBLE);
//                list =new PushToFire(tensp,soluong,addtocart);
            }
            Log.d("arr1",arrayList.size()+"");
        } else {

            bntluu.setEnabled(false);
            tvkhongsanpham.setVisibility(View.VISIBLE);

        }
    }


    private  void pushData(ArrayList<PushToFire> list,long date,boolean flag){
        bntluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("sanphamorder");
                if(list.size()>0){
                    FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("khuvuc").child(id_khuvuc).child("ban").child(id_ban).child("trangthai").setValue("2");
                }
//                for(int i =0; i < list.size();i++){
//                    String id_sp =databaseReference.push().getKey();
//                    databaseReference.child(id).child("sanpham").child(id_sp).setValue(list.get(i));
//                }
//                databaseReference.child(id).child("sanpham").child().setValue();

                pushToFire1 = new  PushToFire1(date,flag,list);
                databaseReference.child(id).setValue(pushToFire1);

            }
        });
    }

}
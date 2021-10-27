package java.android.quanlybanhang.function.CardProductSQL;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.android.quanlybanhang.Model.ChucNangThanhToan.DonGia;
import java.android.quanlybanhang.Model.Product;
import java.android.quanlybanhang.Model.ChucNangThanhToan.ProductPushFB;
import java.android.quanlybanhang.HelperClasses.Package_CartAdapter_SQL.StaticCardAdapter;
import java.android.quanlybanhang.Model.ChucNangThanhToan.ProuductPushFB1;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.database.Database_order;

import java.android.quanlybanhang.function.MainActivity;
import java.android.quanlybanhang.function.ThanhToanActivity;
import java.sql.Timestamp;
import java.util.ArrayList;

public class CardProduct extends AppCompatActivity {
    private Toolbar toolbar;//tool bar khai bao id
    private RecyclerView rv_3;//tool bar khai bao id
     StaticCardAdapter staticCartAdapter;
     ArrayList<Product> listcard;
    ProuductPushFB1 list;
    ArrayList<DonGia> cardProducts;
    ArrayList<ProuductPushFB1> listSP;
     private ProductPushFB productPushFB;
     String id_ban;
     String id_khuvuc;
     String id;
     String a;

     private Product staticMonOrderModel;
     Button bntluu,bntthanhtoan;
    private Database_order database_order;
    private ProuductPushFB1 prouductPushFB1;
     private boolean flag;
     String yeuCau;
     int trangThai=0;

     private TextView tvkhongsanpham,tvtentongsp;
    private final String TEN_BANG="ProductSQL1";
   public static int soluong2;
    TextView tvsoluong,tongsanphams;

Activity activity;

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
        tongsanphams= findViewById(R.id.Tongsanpham);

        //
        Intent intent = getIntent();
        Log.d("id_khuvuc_Truong","KS");
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

        long date =Long.parseLong(timestamp.getTime()+"");

        getDulieuSql();

        pushData(listSP,date,flag,trangThai);
        staticCartAdapter = new StaticCardAdapter();
        staticCartAdapter.Setdata(listcard, new StaticCardAdapter.card() {
            @Override
            public void TinhTongTien(Double tongtien) {
                tongsanphams.setText("$"+tongtien) ;
            }
        });

        for (int i=0; i<listcard.size();i++){
            listcard.get(i).getSoluong();
            Log.d("soluong2",listcard.get(i).getSoluong()+"+1");
        }
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
                "loai TEXT, " +
                "yeuCau TEXT);");
        Log.d("aaaaa","aaaa");


        ArrayList<Product> arrayList = new ArrayList<>();
        String S="SELECT * FROM "+TEN_BANG+" WHERE Id='"+id+"'";
        Cursor cursor =  database_order.GetData(S,null);
        Log.d("11111",cursor+"1111");
        cardProducts = new ArrayList<>();
        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                a = cursor.getString(0);
                    String tensp= cursor.getString(1);
                    int  soluong= cursor.getInt(2);
                    Log.d("cosql",soluong+"");
                    String img= cursor.getString(3);
                    double  gia= cursor.getDouble(4);
                    String Loai = cursor.getString(5);
                    String yeuCau = cursor.getString(6);
//                    Log.d("yeuCauSQL1",yeuCau);
//                    Log.d("loai_gia",Loai+"+"+gia+"");
                     cardProducts.add(new DonGia(Loai,gia));
                    listcard.add(new Product(a,tensp,soluong,img,cardProducts));
                    listSP.add(new ProuductPushFB1(Loai,tensp,yeuCau,img,gia,soluong));
                tvtentongsp.setVisibility(View.VISIBLE);
//                list =new PushToFire(tensp,soluong,addtocart);
            }
            Log.d("arr1",arrayList.size()+"");
        } else {

            bntluu.setEnabled(false);
            tvkhongsanpham.setVisibility(View.VISIBLE);

        }
    }


    private  void pushData(ArrayList<ProuductPushFB1> list, long date, boolean flag,int trangThai){
        bntluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hamxulicardt();
                 productPushFB = new ProductPushFB(date,flag,trangThai,list);
                 FirebaseDatabase.getInstance().getReference().child("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("sanphamorder").child(id).setValue(productPushFB);
                if(list.size()>0){
                    FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("khuvuc").child(id_khuvuc).child("ban").child(id_ban).child("trangthai").setValue("2");
                    FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("khuvuc").child(id_khuvuc).child("ban").child(id_ban).child("gioDaOder").setValue(date);
                }

                Intent intent = new Intent(CardProduct.this, ThanhToanActivity.class);
                intent.putExtra("id_ban",id_ban);
                intent.putExtra("id_khuvuc",id_khuvuc);
                startActivity(intent);
//                XoaSpkhiOrder();
                Toast.makeText(CardProduct.this,"Order Thành Công",Toast.LENGTH_LONG).show();

            }
        });

    }
    private void Hamxulicardt(){
        ArrayList<Product> lists = staticCartAdapter.getItems();
        for(int i=0 ; i<lists.size();i++){
            listSP.get(i).setSoluong(lists.get(i).getSoluong());
        }
    }

}
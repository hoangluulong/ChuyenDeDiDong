package java.android.quanlybanhang.function.ChiTietOrder;

import androidx.annotation.NonNull;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.android.quanlybanhang.HelperClasses.Package_ApdaterLoaiGia.AdapterLoaiGia;
import java.android.quanlybanhang.Model.ChucNangThanhToan.DonGia;
import java.android.quanlybanhang.Model.Product;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.database.Database_order;
import java.util.ArrayList;

public class ChiTietSanPham extends AppCompatActivity {
    private Toolbar toolbar;//tool bar khai bao id
    Product staticMonOrderModel;
    TextView tensp,giasp,giatongsp;
    String tenban;
    String id_ban;
    String id_khuvuc;
    String id;
    private  int sl;
    private Database_order database_order;
    private Button bnt_xacnhan;
    private  String tensps;
    private   String image;
    private Double giasanphams;
    private  int soluong;
    private  TextView soluong2,tonggiasp;
    private ImageView imgsp,plus,minus;
    private  int sluong=1;
    private EditText yeuCau;
    ArrayList<DonGia> donGiaOrders;
    String YeuCau1;
    Double numcheck=0.0;
    String Loai;
    ArrayList<Product> items;
    Double gia;
    String key_sp;
    String S;
    String id_datban;
    String trangthai;
    ArrayList<String> listnew;
    private int position;
    private ArrayList<DonGia>arrdongia;
   private RecyclerView recyclerView;
   private AdapterLoaiGia adapterLoaiGia;
    private DatabaseReference mDatabase;//khai bao database
     private final String TEN_BANG="ProductSQL1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("nnn","nnnnnnn");

        getData();

        setContentView(R.layout.activity_chi_tiet_san_pham_card);
        toolbar = findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
//             viet su kien cho toolbar
        ActionBar actionBar = getSupportActionBar();
//Thiết lập tiêu đề nếu muốn
        actionBar.setTitle("");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
//        StaticMonOrderModel staticMonOrderModel = getIntent().getSerializableExtra("sp");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
//        id_khuvuc = intent.getStringExtra("id_khuvuc");
        key_sp = intent.getStringExtra("key_sanpham");

        staticMonOrderModel = (Product) bundle.getSerializable("sp");
        Log.d("key_sanpham",key_sp+"Truong");
        tensps = staticMonOrderModel.getNameProduct();
        image = staticMonOrderModel.getImgProduct();
        soluong = staticMonOrderModel.getSoluong();
        donGiaOrders = staticMonOrderModel.getDonGia();
        mDatabase = FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("MangDi");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    trangthai= snapshot1.getValue()+"";
                    Log.d("TrangThaima",trangthai+"chitiet");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        soluong2 = findViewById(R.id.tvsoluong);
//        Log.d("bbb","tensp"+tensps+"giasanpham"+giasanphams+"soluong"+soluong+"");
//        lay id
        tensp = findViewById(R.id.tvtensanpham);
        tonggiasp = findViewById(R.id.tvtonggiasanpham);
        imgsp = findViewById(R.id.imgproduct);
        minus = findViewById(R.id.bnt_minus);
        plus = findViewById(R.id.bnt_plus);
        yeuCau =(EditText) findViewById(R.id.edt_ghichu);
        recyclerView = findViewById(R.id.rv_3);
//
        arrdongia = new  ArrayList<>();
        for(int i = 0; i< donGiaOrders.size(); i++){

            if(donGiaOrders.get(i).getId().equals( staticMonOrderModel.getId())){
                arrdongia.add(new DonGia(donGiaOrders.get(i).getTenDonGia(), donGiaOrders.get(i).getGiaBan()));


            }


        }

        adapterLoaiGia = new AdapterLoaiGia(arrdongia,numcheck,tonggiasp,soluong2,Loai,gia,position);
           recyclerView.setLayoutManager(new LinearLayoutManager(ChiTietSanPham.this, LinearLayoutManager.VERTICAL, false));
           recyclerView.setAdapter(adapterLoaiGia);
           adapterLoaiGia.notifyDataSetChanged();

//        do du lieu vao trang
        tensp.setText(tensps);
        Picasso.get().load(image).into(imgsp);

        id_ban = bundle.getString("id_ban");
        id_khuvuc= bundle.getString("id_khuvuc");
        id_datban = bundle.getString("id_datban");
        id=id_ban+"_"+id_khuvuc;


        Log.d("codetruong",id_datban+"Chitiet");
        sl=Integer.parseInt(soluong2.getText()+"");
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double num=0.0;
                for(int i=0;i<arrdongia.size();i++){
                    if(arrdongia.get(i).getCheck()){
                        num=arrdongia.get(i).getGiachung();

                    }
                }
                sl=Integer.parseInt(soluong2.getText()+"");
//                int sluong=0;
                sluong= sl+1;
                tonggiasp.setText((sluong*num)+"");
                soluong2.setText(sluong+"");
//                Toast.makeText(ChiTietSanPham.this,soluong2.getText()+"",Toast.LENGTH_LONG).show();
//                tonggiasp.setText((giasanphams*sluong)+"");
            }
        });
//        nut trừ
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double num=0.0;
                for(int i=0;i<arrdongia.size();i++){
                    if(arrdongia.get(i).getCheck()){

                        num=arrdongia.get(i).getGiachung();
                        Log.d("numm", num+"aaaaaaaaaaaaaaaaaaaa");

                    }
                }
                sl=Integer.parseInt(soluong2.getText()+"");

                if(sl>1){
                    sluong= sl-1;
                    tonggiasp.setText((sluong*num)+"");
                    soluong2.setText(sluong+"");
                }
                else {
                    sl=Integer.parseInt(soluong2.getText()+"");
                }


            }
        });

        bnt_xacnhan = findViewById(R.id.bnt_xacnhan);
        bnt_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                for(int i=0;i<arrdongia.size();i++){
                    if(arrdongia.get(i).getCheck()){
                        Loai=arrdongia.get(i).getTenLoaiChung();
                        Log.d("loaiMM",Loai);
                        gia=arrdongia.get(i).getGiachung();
                    }
                }
                    onBackPressed();
                getDulieuSql(Loai,gia);


            }
        });

    }
    private void getData(){
        Log.d("aaaaa","aaaa");
        database_order= new Database_order(this,"app_database.sqlite",null,2);
        Log.d("aaaaa","aaaas");
        database_order.QueryData("CREATE TABLE IF NOT EXISTS "+TEN_BANG+"(" +
                "Id VARCHAR(20)," +
                "tensanpham VARCHAR(50), " +
                "soluong INTEGER DEFAULT 0, " +
                "image TEXT, " +
                "gia DOUBLE, " +
                "loai TEXT, " +
                "yeuCau TEXT);");

    }
    private  void getDulieuSql( String Loai,Double gia){
        database_order= new Database_order(this,"app_database.sqlite",null,2);
        ArrayList<Product> arrayList = new ArrayList<>();
        Log.d("TrangThaine",trangthai);
        if(trangthai.equals("1")) {

            S = "SELECT * FROM " + TEN_BANG + " WHERE Id='" + id_datban + "' AND tensanpham='" + tensps + "'  AND loai='" + Loai + "' ";


        }
        else if(trangthai.equals("0")) {

            S = "SELECT * FROM " + TEN_BANG + " WHERE Id='" + id + "' AND tensanpham='" + tensps + "'  AND loai='" + Loai + "' ";

        }
        Cursor cursor =  database_order.GetData(S,null);
        Log.d("sllll",cursor.getCount()+"couser");
        if (cursor.getCount() > 0) {
            int  soluong1=0;
        Log.d("yeuCaumss",yeuCau.getText().toString()+"ne");
            sluong=Integer.parseInt(soluong2.getText()+"");
            Log.d("sllll",sl+"");
            while (cursor.moveToNext()) {
               String a = cursor.getString(0);
                String tensp= cursor.getString(1);
                soluong1= cursor.getInt(2);
                String img= cursor.getString(3);
                double  gias= cursor.getInt(4);
                arrayList.add(new Product(a,tensp,soluong1,img,gias));
            }
            Log.d("bbbs","aaaaaabbs");
            Log.d("TrangThaine",trangthai);
            if( trangthai.equals("1")){
                database_order.QueryData("UPDATE "+TEN_BANG+" SET soluong = "+(soluong1+sluong)+" WHERE Id= '"+id_datban+"' AND tensanpham= '"+tensps+"' AND loai='"+Loai+"'");
                Log.d("TrangThaine",trangthai+"1A");
            }else if(trangthai.equals(" ")) {
                Log.d("TrangThaine",trangthai +"1B");
                database_order.QueryData("UPDATE "+TEN_BANG+" SET soluong = "+(soluong1+sluong)+" WHERE Id= '"+id+"' AND tensanpham= '"+tensps+"' AND loai='"+Loai+"'");

            }
        } else {
            Log.d("yeuCau","aaaaaabb");
            sluong=Integer.parseInt(soluong2.getText()+"");
            YeuCau1 =yeuCau.getText().toString();
            Log.d("yeuCaumss",yeuCau.getText().toString()+"nen");
            if(trangthai.equals("1")) {
                Log.d("TrangThaine",trangthai +"1C");
                database_order.QueryData("INSERT INTO " + TEN_BANG + " VALUES('" + id_datban + "','" + tensps + "'," + sluong + ",'" + image + "'," + gia + ",'" + Loai + "','" + yeuCau.getText().toString() + "');");

            }
            else if (trangthai.equals("0")){
                Log.d("TrangThaine",trangthai +"1D");
                database_order.QueryData("INSERT INTO " + TEN_BANG + " VALUES('" + id_ban + "_" + id_khuvuc + "','" + tensps + "'," + sluong + ",'" + image + "'," + gia + ",'" + Loai + "','" + yeuCau.getText().toString() + "');");


            }
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
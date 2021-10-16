package java.android.quanlybanhang.ChiTietSanPham;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.android.quanlybanhang.OrderMon.Product;
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
        staticMonOrderModel = (Product) bundle.getSerializable("sp");

         tensps = staticMonOrderModel.getNameProduct();

        image = staticMonOrderModel.getImgProduct();
        giasanphams= staticMonOrderModel.getGiaBan();
         soluong = staticMonOrderModel.getSoluong();
         soluong2 = findViewById(R.id.tvsoluong);
        Log.d("bbb","tensp"+tensps+"giasanpham"+giasanphams+"soluong"+soluong+"");
//        lay id
        tensp = findViewById(R.id.tvtensanpham);
        giasp = findViewById(R.id.tvgiasanpham);
        tonggiasp = findViewById(R.id.tvtonggiasanpham);
        imgsp = findViewById(R.id.imgproduct);
        minus = findViewById(R.id.bnt_minus);
        plus = findViewById(R.id.bnt_plus);
//        do du lieu vao trang
        tensp.setText(tensps);
        Picasso.get().load(image).into(imgsp);
        giasp.setText(giasanphams+"");

//        tenban = bundle.getString("tenban") ;
        id_ban = bundle.getString("id_ban");
        id_khuvuc= bundle.getString("id_khuvuc");
        id=id_ban+"_"+id_khuvuc;
//        Log.d("id_ban",id_ban+"acbank");
//        Log.d("id_khuvuc",id_ban+"acbank");
//        Log.d("aaa",tenban+"acbank");

        sl=Integer.parseInt(soluong2.getText()+"");
        tonggiasp.setText((giasanphams*sl)+"");
//              nút cộng
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sl=Integer.parseInt(soluong2.getText()+"");
//                int sluong=0;
                sluong= sl+1;

                soluong2.setText(sluong+"");
//                Toast.makeText(ChiTietSanPham.this,soluong2.getText()+"",Toast.LENGTH_LONG).show();
                tonggiasp.setText((giasanphams*sluong)+"");
            }
        });
//        nut trừ
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sl=Integer.parseInt(soluong2.getText()+"");

                if(sl>1){
                    sluong= sl-1;
                    soluong2.setText(sluong+"");
//                    Toast.makeText(ChiTietSanPham.this,soluong2.getText()+"",Toast.LENGTH_LONG).show();
                    tonggiasp.setText((giasanphams*sluong)+"");
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
                onBackPressed();
                getDulieuSql();

            }
        });


    }
    private void getData(){
        database_order= new Database_order(this,"app_database.sqlite",null,1);
        database_order.QueryData("CREATE TABLE IF NOT EXISTS databaseorder2(" +
                "Id VARCHAR(20)," +
                "tensanpham VARCHAR(50), " +
                "soluong INTEGER DEFAULT 0, " +
                "image TEXT, " +
                "gia DOUBLE);");
    }
    private  void getDulieuSql( ){
        database_order= new Database_order(this,"app_database.sqlite",null,1);
//        database_order.GetData("SELECT * FROM databaseorde1r ");
        ArrayList<Product> arrayList = new ArrayList<>();
        String S="SELECT * FROM databaseorder2 WHERE Id='"+id+"' AND tensanpham='"+tensps+"'";
        Cursor cursor =  database_order.GetData(S,null);
        Log.d("11111",cursor+"1111");

        if (cursor.getCount() > 0) {
            int  soluong1=0;
            sluong=Integer.parseInt(soluong2.getText()+"");
            Log.d("sllll",sl+"");
            while (cursor.moveToNext()) {

               String a = cursor.getString(0);
                Log.d("cosql",a);
//                Log.d("cosql",id+"nn");

                String tensp= cursor.getString(1);
                soluong1= cursor.getInt(2);
                String img= cursor.getString(3);
                double  gia= cursor.getInt(4);
                arrayList.add(new Product(a,tensp,soluong1,img,gia));
            }
            database_order.QueryData("UPDATE databaseorder2 SET soluong = "+(soluong1+sluong)+" WHERE id= '"+id+"' AND tensanpham='"+tensps+"'");
//            Log.d("arr1",arrayList.size()+"");
//            Log.d("logsoluong",(soluong1+sluong)+"a");

        } else {
            sluong=Integer.parseInt(soluong2.getText()+"");

            database_order.QueryData("INSERT INTO databaseorder2 VALUES('"+id_ban+"_"+id_khuvuc+"','"+tensps+"',"+sluong+",'"+image+"',"+giasanphams+");");
//            Log.d("logsoluong",(sluong)+"");
        }

    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }

}
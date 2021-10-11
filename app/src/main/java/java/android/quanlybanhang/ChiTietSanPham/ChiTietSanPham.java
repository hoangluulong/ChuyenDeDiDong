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
import android.widget.TextView;

import java.android.quanlybanhang.OrderMon.Product;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.database.Database_order;
import java.android.quanlybanhang.function.MonOrder;
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
    Button bnt_xacnhan;
private  String tensps;
private   String image;
private Double giasanphams;
private  int soluong;
private  TextView soluong2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("nnn","nnnnnnn");

        getData();

        setContentView(R.layout.activity_chi_tiet_san_pham_do_uong);
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
        tensp = findViewById(R.id.tvtensanpham);
        giasp = findViewById(R.id.tvgiasanpham);
        giatongsp = findViewById(R.id.tvgiatongsanpham);
        tensp.setText(tensps);
        giasp.setText(giasanphams+"");
//        tenban = bundle.getString("tenban") ;
        id_ban = bundle.getString("id_ban");
        id_khuvuc= bundle.getString("id_khuvuc");
        id=id_ban+"_"+id_khuvuc;
//        Log.d("id_ban",id_ban+"acbank");
//        Log.d("id_khuvuc",id_ban+"acbank");
//        Log.d("aaa",tenban+"acbank");
        getDulieuSql();
        bnt_xacnhan = findViewById(R.id.bnt_xacnhan);
        bnt_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                        AddFirebasedata(new StaticMonOrderModel(tensps,soluong));
//                database_order.QueryData("INSERT INTO databaseorder VALUES('"+id_ban+"_"+id_khuvuc+"','"+tensps+"',"+soluong+",'"+image+"',"+giasanphams+");");
//                Intent intent1 = new Intent(ChiTietSanPham.this, MonOrder.class);
//                intent.putExtra("id_ban",id_ban);
//                intent.putExtra("id_khuvuc",id_khuvuc);
//                Log.d("id_ban",id_ban+"acbank");
//                Log.d("id_khuvuc",id_ban+"acbank");
                onBackPressed();
//                startActivity(intent1);
//                Bundle bundle1= new Bundle();
//                bundle1.putSerializable("card",new Product(tensps,soluong));
//                intent1.putExtras(bundle1);
////                        intent1.putExtra("tenban",tenban);
//                startActivityForResult(intent1,RESULT_OK);


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
             sl=Integer.parseInt(soluong2.getText()+"");
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
            database_order.QueryData("UPDATE databaseorder2 SET soluong = "+(soluong1+sl)+" WHERE id= '"+id+"' AND tensanpham='"+tensps+"'");
            Log.d("arr1",arrayList.size()+"");

        } else {
            sl=Integer.parseInt(soluong2.getText()+"");
            database_order.QueryData("INSERT INTO databaseorder2 VALUES('"+id_ban+"_"+id_khuvuc+"','"+tensps+"',"+sl+",'"+image+"',"+giasanphams+");");
        }

    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }
    //        database_order.QueryData("CREATE TABLE IF NOT EXISTS database1(" +
//                "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                "NgayBatDau VARCHAR(200), " +
//                "NgayKetThuc VARCHAR(200), " +
//                "KieuHienThi INTEGER DEFAULT 1);");
//    }
//
//    public void AddFirebasedata(StaticMonOrderModel  staticMonOrderModel){
//
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("sanphamorder");
//        databaseReference.child(tenban).child("sanpham").push().setValue(staticMonOrderModel);
//
//    }

}
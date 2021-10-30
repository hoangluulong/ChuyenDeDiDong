package java.android.quanlybanhang.function;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.android.quanlybanhang.HelperClasses.Package_ThanhToanAdapter.ThanhToanAdapter;
import java.android.quanlybanhang.Model.ChucNangThanhToan.ProductPushFB;
import java.android.quanlybanhang.Model.ChucNangThanhToan.ProuductPushFB1;
import java.android.quanlybanhang.R;
import java.android.quanlybanhang.database.Database_order;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ThanhToanActivity extends AppCompatActivity {
private ArrayList<String> list ;
private String id_ban,id_khuvuc;
private DatabaseReference mDatabase;
private ThanhToanAdapter thanhToanAdapter;
private RecyclerView recyclerView;
private ImageView imgCart;
private TextView nameactivity;
private Database_order database_order;
private final String TEN_BANG="ProductSQL1";
    String id;
private int kt=-1;
Double giaTong=0.0;
private Button bnt_thanhtoan;
private TextView totalTxt;
private  ArrayList<ProuductPushFB1> listmon = new ArrayList<>();
    private Toolbar toolbar;//tool bar khai bao id
    private  ArrayList<ProductPushFB> ListDate_yc = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_thanhtoan);
        toolbar = findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
//viet su kien cho toolbar
        getDatasql();
        hamlaydate();
        Hamlaygiohientai();
        ActionBar actionBar = getSupportActionBar();
//Thiết lập tiêu đề nếu muốn
        actionBar.setTitle("Thanh Toán");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        totalTxt = findViewById(R.id.totalTxt) ;

        Intent intent1 = getIntent();
        id_ban = intent1.getStringExtra("id_ban");
//        Log.d("getkeyabc",id_ban+"getabc");

        id_khuvuc = intent1.getStringExtra("id_khuvuc");
        imgCart = findViewById(R.id.img_order);
        bnt_thanhtoan = findViewById(R.id.bnt_thanhtoan);
        id=id_ban+"_"+id_khuvuc;
        list= new ArrayList<>();

        OrderCart();
        getData();
        OnclickThanhtoan();
//        kiemtra();


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
                   listmon = new ArrayList<ProuductPushFB1>();

                   Log.d("dateFirebase", snapshot.child("date").getValue() + "");
                   String flag = snapshot.child("flag").getValue() + "";
                   DataSnapshot sss = snapshot.child("sanpham");
                   for (DataSnapshot postSnapshot : sss.getChildren()) {
                       String nameProduct = postSnapshot.child("nameProduct").getValue() + "";
                       int soluong = Integer.parseInt(postSnapshot.child("soluong").getValue() + "");
                       String yeuCau = postSnapshot.child("yeuCau").getValue() + "";
                       Double giaProudct = Double.parseDouble(postSnapshot.child("giaProudct").getValue() + "");
                       String Loai = postSnapshot.child("loai").getValue() + "";
                       String imgproduct = postSnapshot.child("imgProduct").getValue() + "";
                       listmon.add(new ProuductPushFB1(Loai, nameProduct, yeuCau, imgproduct, giaProudct, soluong));
                   }
                   //lấy giá Tổng Của Tất cà
                   for(int i=0;i<listmon.size();i++){
                       giaTong += listmon.get(i).getGiaProudct();
                   }
                   totalTxt.setText(giaTong+"");
                   ListDate_yc.add(new ProductPushFB(1, true, listmon));

                   recyclerView = findViewById(R.id.rv_3);
                    //đô dữ liệu vào Thanh Toám
                   thanhToanAdapter = new ThanhToanAdapter(listmon);
                   recyclerView.setLayoutManager(new LinearLayoutManager(ThanhToanActivity.this, LinearLayoutManager.VERTICAL, false));
                   recyclerView.setFocusable(false);
                   recyclerView.setAdapter(thanhToanAdapter);
                   thanhToanAdapter.notifyDataSetChanged();


               }
               else {
                   bnt_thanhtoan.setEnabled(false);
               }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void OnclickThanhtoan(){

        bnt_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("bienlai").child("thu").child(hamlaydate()).child(Hamlaygiohientai());
                databaseReference.child("id_ban").setValue(id_ban);
                databaseReference.child("id_khuvuc").setValue(id_khuvuc);
                databaseReference.child("tongtien").setValue(giaTong);
                databaseReference.child("status").setValue(1);
                databaseReference.child("id_khachhang").setValue("aaa");
                databaseReference.child("id_nhanvien").setValue("aaa");
                databaseReference.child("sanpham").setValue(listmon).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ThanhToanActivity.this,"Thanh Toán Thanh công",Toast.LENGTH_LONG).show();
                         Intent intent = new Intent(ThanhToanActivity.this,OrderMenu.class);
                         intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                        XoaSpkhiOrder();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ThanhToanActivity.this,"Thanh Toán không Thanh công",Toast.LENGTH_LONG).show();
                    }
                });
                FirebaseDatabase.getInstance().getReference().child("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("sanphamorder").child(id_ban+"_"+id_khuvuc).removeValue();
                FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("khuvuc").child(id_khuvuc).child("ban").child(id_ban).child("trangthai").setValue("1");
                FirebaseDatabase.getInstance().getReference("JxZOOK1RzcMM7pL5I6naGZfYSsu2").child("khuvuc").child(id_khuvuc).child("ban").child(id_ban).child("gioDaOder").setValue(0);

            }
        });

    }
    public  String hamlaydate(){
        String date = new SimpleDateFormat("dd-MM-YYYY", Locale.getDefault()).format(new Date());
        Log.d("datenowww",date+"");
        return date;
    }
    public String Hamlaygiohientai(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Log.d("datenowww",timestamp.getTime()+"");
        return  timestamp.getTime()+"";
    }
    private void getDatasql(){

        database_order= new Database_order(this,"app_database.sqlite",null,2);

        database_order.QueryData("CREATE TABLE IF NOT EXISTS "+TEN_BANG+"(" +
                "Id VARCHAR(20)," +
                "tensanpham VARCHAR(50), " +
                "soluong INTEGER DEFAULT 0, " +
                "image TEXT, " +
                "gia DOUBLE, " +
                "loai TEXT, " +
                "yeuCau TEXT);");
        Log.d("aaaaa","aaaa");
    }

    private void XoaSpkhiOrder(){
        database_order.QueryData(" DELETE FROM "+TEN_BANG+" WHERE Id='"+id+"'");

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ThanhToanActivity.this,OrderMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
package java.android.quanlybanhang.function;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.google.gson.Gson;

import java.android.quanlybanhang.HelperClasses.Package_ThanhToanAdapter.ThanhToanAdapter;
import java.android.quanlybanhang.HelperClasses.Pakage_AdapterBan.StaticBanModel;
import java.android.quanlybanhang.HelperClasses.Pakage_AdapterKhuVuc.StaticModelKhuVuc;
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
    private ArrayList<String> list;
    private String id_ban, id_khuvuc;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase1;
    private DatabaseReference mDatabasea;
    private ThanhToanAdapter thanhToanAdapter;
    private RecyclerView recyclerView;

    private TextView nameactivity;
    private Database_order database_order;
    private final String TEN_BANG = "ProductSQL1";
    String id;
    private int kt = -1;
    Double giaTong = 0.0;
    private Button bnt_thanhtoan;
    private TextView totalTxt;
    private String trangthai;
    private String id_datban;
    private ArrayList<ProuductPushFB1> listmon ;
    private Toolbar toolbar;
    private ArrayList<ProductPushFB> ListDate_yc ;
    private Dialog dialogban;
    Window window;
    ImageButton bnt_threedot;
    TextView gopban,chuyenban,tachban;
    Long date;
    String code1;
    public String keyIDCuaHang = "JxZOOK1RzcMM7pL5I6naGZfYSsu2";

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
        totalTxt = findViewById(R.id.totalTxt);
        bnt_threedot = findViewById(R.id.bnt_threedot);
        Intent intent1 = getIntent();
        id_ban = intent1.getStringExtra("id_ban");
//        Log.d("getkeyabc",id_ban+"getabc");
        id_khuvuc = intent1.getStringExtra("id_khuvuc");
        id_datban = intent1.getStringExtra("id_datban");
        OnclickMenuchucnang();
        bnt_thanhtoan = findViewById(R.id.bnt_thanhtoan);
        id = id_ban + "_" + id_khuvuc;
        list = new ArrayList<>();
        int code = (int) Math.floor(((Math.random() * 899999) + 100000));
        code1=code+"";
        mDatabase1 = FirebaseDatabase.getInstance().getReference(keyIDCuaHang).child("MangDi");
        mDatabase1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    trangthai = snapshot1.getValue() + "";
                    Log.d("TrangThaima", trangthai + "chitiet");
                    getData();
                    if(trangthai.equals("1")){
                        bnt_threedot.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        OnclickThanhtoan();
//        kiemtra();


    }

    public void getData() {
        Log.d("PXTPRO", trangthai+"");
        if (trangthai.equals("0")) {
            if(id_ban + "_" + id_khuvuc!=null){
                mDatabase = FirebaseDatabase.getInstance().getReference(keyIDCuaHang).child("sanphamorder").child(id_ban + "_" + id_khuvuc);
            }
        }
        if (trangthai.equals("1")) {
            if(id_datban!=null){
                mDatabase = FirebaseDatabase.getInstance().getReference(keyIDCuaHang).child("sanphamorder").child(id_datban);
            }
        }

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    ListDate_yc = new ArrayList<>();
                    Log.d("kssj","ssks");
                    listmon = new ArrayList<ProuductPushFB1>();
                    ArrayList<ProuductPushFB1> mm = new ArrayList<>();
                    Long date1 = Long.parseLong(snapshot.child("date").getValue()+"");
                    Boolean flag = Boolean.parseBoolean(snapshot.child("flag").getValue()+"");
                    int trangThais =Integer.parseInt(snapshot.child("trangThai").getValue()+"") ;
                    DataSnapshot sss = snapshot.child("sanpham");
                    for (DataSnapshot postSnapshot : sss.getChildren()) {
                        String nameProduct = postSnapshot.child("nameProduct").getValue() + "";
                        int soluong = Integer.parseInt(postSnapshot.child("soluong").getValue() + "");
                        String yeuCau = postSnapshot.child("yeuCau").getValue() + "";
                        Double giaProudct = Double.parseDouble(postSnapshot.child("giaProudct").getValue() + "");
                        String Loai = postSnapshot.child("loai").getValue() + "";
                        String imgproduct = postSnapshot.child("imgProduct").getValue() + "";
                        listmon.add(new ProuductPushFB1(Loai, nameProduct, yeuCau, imgproduct, giaProudct, soluong));
                        mm.add(new ProuductPushFB1(Loai, nameProduct, yeuCau, imgproduct, giaProudct, soluong));
                    }
                    ProductPushFB product = new ProductPushFB(date1, flag,trangThais, mm);
                    ListDate_yc.add(product);

//


                    for (int i = 0; i < listmon.size(); i++) {
                        giaTong += listmon.get(i).getGiaProudct()*listmon.get(i).getSoluong();
                    }
                    totalTxt.setText(giaTong + "");
                    recyclerView = findViewById(R.id.rv_3);
                    //đô dữ liệu vào Thanh Toám
                    thanhToanAdapter = new ThanhToanAdapter(listmon);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ThanhToanActivity.this, LinearLayoutManager.VERTICAL, false));
                    recyclerView.setFocusable(false);
                    recyclerView.setAdapter(thanhToanAdapter);
                    thanhToanAdapter.notifyDataSetChanged();


                } else {
                    bnt_thanhtoan.setEnabled(false);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void OnclickThanhtoan() {

        bnt_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trangthai.equals("0")) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(keyIDCuaHang).child("bienlai").child("thu").child(hamlaydate()).child(Hamlaygiohientai());
                    databaseReference.child("id_ban").setValue(id_ban);
                    databaseReference.child("id_khuvuc").setValue(id_khuvuc);
                    databaseReference.child("tongtien").setValue(giaTong);
                    databaseReference.child("status").setValue(1);
                    databaseReference.child("id_khachhang").setValue("aaa");
                    databaseReference.child("id_nhanvien").setValue("aaa");
                    databaseReference.child("sanpham").setValue(listmon).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(ThanhToanActivity.this, "Thanh Toán Thanh công", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ThanhToanActivity.this, OrderMenu.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            XoaSpkhiOrder();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ThanhToanActivity.this, "Thanh Toán không Thanh công", Toast.LENGTH_LONG).show();
                        }
                    });
                    FirebaseDatabase.getInstance().getReference().child(keyIDCuaHang).child("sanphamorder").child(id_ban + "_" + id_khuvuc).removeValue();
                    FirebaseDatabase.getInstance().getReference(keyIDCuaHang).child("khuvuc").child(id_khuvuc).child("ban").child(id_ban).child("trangthai").setValue("1");
                    FirebaseDatabase.getInstance().getReference(keyIDCuaHang).child("khuvuc").child(id_khuvuc).child("ban").child(id_ban).child("gioDaOder").setValue(0);

                } else if (trangthai.equals("1")) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(keyIDCuaHang).child("bienlai").child("thu").child(hamlaydate()).child(Hamlaygiohientai());
                    databaseReference.child("id_datban").setValue(id_datban);
                    databaseReference.child("tongtien").setValue(giaTong);
                    databaseReference.child("status").setValue(1);
                    databaseReference.child("id_khachhang").setValue("aaa");
                    databaseReference.child("id_nhanvien").setValue("aaa");
                    databaseReference.child("sanpham").setValue(listmon).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(ThanhToanActivity.this, "Thanh Toán Thanh công", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ThanhToanActivity.this, OrderMenu.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            XoaSpkhiOrder();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ThanhToanActivity.this, "Thanh Toán không Thanh công", Toast.LENGTH_LONG).show();
                        }
                    });
                    FirebaseDatabase.getInstance().getReference().child(keyIDCuaHang).child("sanphamorder").child(id_datban).removeValue();
                }
            }
        });

    }

    public String hamlaydate() {
        String date = new SimpleDateFormat("dd-MM-YYYY", Locale.getDefault()).format(new Date());
        Log.d("datenowww", date + "");
        return date;
    }

    public String Hamlaygiohientai() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Log.d("datenowww", timestamp.getTime() + "");
        return timestamp.getTime() + "";
    }

    private void getDatasql() {

        database_order = new Database_order(this, "app_database.sqlite", null, 2);

        database_order.QueryData("CREATE TABLE IF NOT EXISTS " + TEN_BANG + "(" +
                "Id VARCHAR(20)," +
                "tensanpham VARCHAR(50), " +
                "soluong INTEGER DEFAULT 0, " +
                "image TEXT, " +
                "gia DOUBLE, " +
                "loai TEXT, " +
                "yeuCau TEXT);");
        Log.d("aaaaa", "aaaa");
    }

    private void XoaSpkhiOrder() {
        if (trangthai.equals("0")) {
            database_order.QueryData(" DELETE FROM " + TEN_BANG + " WHERE Id='" + id + "'");
        } else if (trangthai.equals("1")) {
            database_order.QueryData(" DELETE FROM " + TEN_BANG + " WHERE Id='" + id_datban + "'");
        }


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ThanhToanActivity.this, OrderMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id = item.getItemId();
        if (item_id == R.id.order) {
            Toast.makeText(this, "order nè", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ThanhToanActivity.this, MonOrder.class);
            intent.putExtra("id_ban", id_ban);
            intent.putExtra("id_khuvuc", id_khuvuc);
            intent.putExtra("id_datban", id_datban);
            startActivity(intent);
        }
        if(item_id==android.R.id.home){
            onBackPressed();
            return true;
        }
        return true;
    }
    private void HamTaodialog(int gravity){

        dialogban = new Dialog(ThanhToanActivity.this);
        dialogban.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogban.setContentView(R.layout.dailongthanhtoan);

        window = dialogban.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        window.setAttributes(windownAttributes);
        if (Gravity.BOTTOM == gravity) {
            dialogban.setCancelable(true);
        } else {
            dialogban.setCancelable(false);
        }
        gopban=dialogban.findViewById(R.id.tvgopban);
        chuyenban = dialogban.findViewById(R.id.tvchuyenban);
        tachban = dialogban.findViewById(R.id.tvtachban);
        OnclickChucnang(gopban,chuyenban,tachban);
        dialogban.show();
    }
    public  void OnclickMenuchucnang(){
        bnt_threedot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HamTaodialog(Gravity.BOTTOM);
            }
        });
    }
    public void OnclickChucnang( TextView gopban, TextView chuyenban,TextView tachban){
        gopban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogban.dismiss();
                Intent intent = new Intent(ThanhToanActivity.this, OrderMenu.class);

                FirebaseDatabase.getInstance().getReference(keyIDCuaHang).child("chucnang").child(code1).child("trangthai").setValue("1");
                intent.putExtra("id_ban", id_ban);
                intent.putExtra("id_khuvuc", id_khuvuc);
                intent.putExtra("id_datban", id_datban);
                intent.putExtra("id_trangthai",code1);
                Log.d("trangthaigopcode",code1+"thanhtoan");
                Gson gson = new Gson();
                String a = gson.toJson(listmon);
                intent.putExtra("list_as_string",a);

                startActivity(intent);
            }
        });
        chuyenban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogban.dismiss();
                Intent intent = new Intent(ThanhToanActivity.this, OrderMenu.class);
                FirebaseDatabase.getInstance().getReference(keyIDCuaHang).child("chucnang").child(code1).child("trangthai").setValue("2");
                intent.putExtra("id_ban", id_ban);
                intent.putExtra("id_khuvuc", id_khuvuc);
                intent.putExtra("id_datban", id_datban);
                intent.putExtra("id_trangthai",code1);
                intent.putExtra("date",date);
                Gson gson = new Gson();
                String a = gson.toJson(listmon);
                String b = gson.toJson(ListDate_yc);
                intent.putExtra("list_as_string",a);
                intent.putExtra("list_as_string1",b);
                startActivity(intent);
            }
        });
        tachban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogban.dismiss();
                Intent intent = new Intent(ThanhToanActivity.this, TachBanActivity.class);

                intent.putExtra("id_ban", id_ban);
                intent.putExtra("id_khuvuc", id_khuvuc);
                intent.putExtra("id_datban", id_datban);
                intent.putExtra("id_trangthai",code1);
                Gson gson = new Gson();
                String b = gson.toJson(ListDate_yc);
                String a = gson.toJson(listmon);
                intent.putExtra("list_as_string",a);
                intent.putExtra("list_as_string1",b);
                startActivity(intent);
            }
        });
    }

}